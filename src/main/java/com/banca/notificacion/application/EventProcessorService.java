package com.banca.notificacion.application;

import com.banca.notificacion.domain.model.Event;
import com.banca.notificacion.domain.model.EventValidationException;
import com.banca.notificacion.domain.model.FraudEvent;
import com.banca.notificacion.domain.model.RiskEvent;
import com.banca.notificacion.domain.port.EventRepository;
import com.banca.notificacion.domain.port.NotificationServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventProcessorService {
    private static final Logger log = LoggerFactory.getLogger(EventProcessorService.class);

    private final EventRepository eventRepository;
    private final NotificationServicePort notificationServicePort;

    public EventProcessorService(EventRepository eventRepository, 
                                  NotificationServicePort notificationServicePort) {
        this.eventRepository = eventRepository;
        this.notificationServicePort = notificationServicePort;
    }

    public Mono<Event> processEvent(Event event) {
        log.info("Procesando evento {} con ID {} de fuente {} en timestamp {}",
                event.getEventType(), event.getEventId(), event.getSource(), event.getTimestamp());

        return validateEvent(event)
            .flatMap(this::checkIdempotency)
            .flatMap(this::applyBusinessRules)
            .flatMap(this::persistAndNotify)
            .doOnSuccess(savedEvent -> log.info("Evento {} procesado exitosamente", savedEvent.getEventId()))
            .doOnError(error -> log.error("Error al procesar evento {}: {}", 
                    event.getEventId(), error.getMessage()));
    }

    public Flux<Event> processEventsBatch(Flux<Event> events) {
        return events
            .flatMap(this::processEvent)
            .doOnComplete(() -> log.info("Lote de eventos procesado completamente"))
            .doOnError(error -> log.error("Error en procesamiento por lotes: {}", error.getMessage()));
    }

    private Mono<Event> validateEvent(Event event) {
        if (event.getEventId() == null) {
            return Mono.error(EventValidationException.missingRequiredField(
                null, event.getEventType(), "eventId"));
        }
        if (event.getEventType() == null || event.getEventType().isBlank()) {
            return Mono.error(EventValidationException.missingRequiredField(
                event.getEventId(), event.getEventType(), "eventType"));
        }
        if (event.getCustomerId() == null) {
            return Mono.error(EventValidationException.missingRequiredField(
                event.getEventId(), event.getEventType(), "customerId"));
        }

        if (event instanceof FraudEvent fraudEvent) {
            return validateFraudEvent(fraudEvent);
        } else if (event instanceof RiskEvent riskEvent) {
            return validateRiskEvent(riskEvent);
        }

        return Mono.just(event);
    }

    private Mono<Event> validateFraudEvent(FraudEvent event) {
        if (event.getAccountNumber() == null || event.getAccountNumber().isBlank()) {
            return Mono.error(EventValidationException.missingRequiredField(
                event.getEventId(), event.getEventType(), "accountNumber"));
        }
        if (event.getTransactionAmount() == null || event.getTransactionAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return Mono.error(EventValidationException.invalidFieldValue(
                event.getEventId(), event.getEventType(), "transactionAmount", event.getTransactionAmount()));
        }
        if (event.getRiskLevel() < 0 || event.getRiskLevel() > 100) {
            return Mono.error(EventValidationException.invalidFieldValue(
                event.getEventId(), event.getEventType(), "riskLevel", event.getRiskLevel()));
        }
        return Mono.just(event);
    }

    private Mono<Event> validateRiskEvent(RiskEvent event) {
        if (event.getRiskType() == null || event.getRiskType().isBlank()) {
            return Mono.error(EventValidationException.missingRequiredField(
                event.getEventId(), event.getEventType(), "riskType"));
        }
        if (event.getRiskScore() < 0 || event.getRiskScore() > 100) {
            return Mono.error(EventValidationException.invalidFieldValue(
                event.getEventId(), event.getEventType(), "riskScore", event.getRiskScore()));
        }
        return Mono.just(event);
    }

    private Mono<Event> checkIdempotency(Event event) {
        return eventRepository.existsByEventId(event.getEventId())
            .flatMap(exists -> {
                if (exists) {
                    log.warn("Evento duplicado detectado: {}", event.getEventId());
                    return Mono.empty();
                }
                return Mono.just(event);
            });
    }

    private Mono<Event> applyBusinessRules(Event event) {
        if (event instanceof FraudEvent fraudEvent) {
            return applyFraudBusinessRules(fraudEvent);
        } else if (event instanceof RiskEvent riskEvent) {
            return applyRiskBusinessRules(riskEvent);
        }
        return Mono.just(event);
    }

    private Mono<Event> applyFraudBusinessRules(FraudEvent event) {
        if (event.isHighRisk()) {
            log.info("Evento de fraude de alto riesgo detectado para cuenta {} - requiere atención inmediata",
                    event.getAccountNumber());
        }
        return Mono.just(event);
    }

    private Mono<Event> applyRiskBusinessRules(RiskEvent event) {
        if (event.isCritical()) {
            log.info("Evento de riesgo crítico detectado para cliente {} - requiere acción inmediata: {}",
                    event.getCustomerId(), event.getRecommendedAction());
        }
        return Mono.just(event);
    }

    private Mono<Event> persistAndNotify(Event event) {
        return eventRepository.save(event)
            .flatMap(savedEvent -> {
                String notificationMessage = buildNotificationMessage(savedEvent);
                return notificationServicePort.sendNotification(
                        savedEvent.getCustomerId(),
                        notificationMessage,
                        savedEvent.getEventType()
                    )
                    .thenReturn(savedEvent);
            });
    }

    private String buildNotificationMessage(Event event) {
        String baseMessage = "Notificación de evento bancario - Tipo: " + event.getEventType();
        
        if (event instanceof FraudEvent fraudEvent) {
            return baseMessage + " - Cuenta: " + fraudEvent.getAccountNumber() + 
                   " - Monto: $" + fraudEvent.getTransactionAmount() + 
                   " - Nivel de riesgo: " + fraudEvent.getRiskLevel() + 
                   " - Descripción: " + fraudEvent.getFraudDescription();
        } else if (event instanceof RiskEvent riskEvent) {
            return baseMessage + " - Tipo de riesgo: " + riskEvent.getRiskType() + 
                   " - Puntuación: " + riskEvent.getRiskScore() + 
                   " - Acción recomendada: " + riskEvent.getRecommendedAction();
        }
        
        return baseMessage + " - ID del evento: " + event.getEventId();
    }
}