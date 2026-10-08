package com.banca.notificacion.infrastructure.adapter;

import com.banca.notificacion.domain.model.Event;
import com.banca.notificacion.domain.port.EventRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
public class EventPublisherAdapter {

    private static final Logger log = LoggerFactory.getLogger(EventPublisherAdapter.class);
    private static final String EVENT_TOPIC = "banking-events";

    private final KafkaTemplate<String, Event> kafkaTemplate;
    private final EventRepository eventRepository;
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;

    public EventPublisherAdapter(
            KafkaTemplate<String, Event> kafkaTemplate,
            EventRepository eventRepository,
            CircuitBreakerRegistry circuitBreakerRegistry,
            RetryRegistry retryRegistry) {
        this.kafkaTemplate = kafkaTemplate;
        this.eventRepository = eventRepository;
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("eventPublisher");
        this.retry = retryRegistry.retry("eventPublisher");
    }

    public Mono<Boolean> publishEvent(Event event) {
        return Mono.fromCallable(() -> {
            log.info("Publishing event {} of type {} to Kafka topic", 
                    event.getEventId(), event.getEventType());
            
            CompletableFuture<SendResult<String, Event>> future = kafkaTemplate.send(
                    EVENT_TOPIC,
                    event.getEventId().toString(),
                    event
            );
            
            SendResult<String, Event> result = future.get();
            log.info("Event {} successfully published to partition {} at offset {}",
                    event.getEventId(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
            
            return true;
        })
        .transformDeferred(CircuitBreaker.operatorOf(circuitBreaker))
        .transformDeferred(Retry.operatorOf(retry))
        .doOnSuccess(success -> {
            if (Boolean.TRUE.equals(success)) {
                log.debug("Event {} published and acknowledged", event.getEventId());
            }
        })
        .doOnError(error -> {
            log.error("Failed to publish event {} after retries: {}", 
                    event.getEventId(), error.getMessage(), error);
        })
        .onErrorResume(error -> {
            log.warn("Circuit breaker open or all retries failed for event {}, " +
                    "storing for later processing", event.getEventId());
            return persistEventForRetry(event)
                    .thenReturn(false);
        });
    }

    private Mono<Void> persistEventForRetry(Event event) {
        return Mono.fromRunnable(() -> {
            try {
                eventRepository.save(event);
                log.info("Event {} persisted for later processing due to publish failure",
                        event.getEventId());
            } catch (Exception e) {
                log.error("Failed to persist event {} for later processing: {}",
                        event.getEventId(), e.getMessage(), e);
                throw new RuntimeException("Failed to queue event for retry", e);
            }
        });
    }

    public Mono<Boolean> publishEventWithFallback(Event event) {
        return publishEvent(event)
                .onErrorResume(e -> {
                    log.error("All publishing attempts failed for event {}, " +
                            "marking as failed permanently", event.getEventId());
                    return Mono.just(false);
                });
    }
}