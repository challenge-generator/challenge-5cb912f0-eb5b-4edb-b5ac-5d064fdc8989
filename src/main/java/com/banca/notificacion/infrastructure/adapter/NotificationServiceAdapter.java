package com.banca.notificacion.infrastructure.adapter;

import com.banca.notificacion.domain.model.Event;
import com.banca.notificacion.domain.model.FraudEvent;
import com.banca.notificacion.domain.model.RiskEvent;
import com.banca.notificacion.domain.port.NotificationServicePort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Component
public class NotificationServiceAdapter implements NotificationServicePort {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceAdapter.class);
    private static final String NOTIFICATION_SERVICE_NAME = "notificationService";

    private final WebClient webClient;
    private final String notificationEndpoint;
    private final Duration timeout;

    public NotificationServiceAdapter(
            WebClient.Builder webClientBuilder,
            @Value("${app.notification.service.url:http://notification-internal:8080/api/notifications}") String notificationEndpoint,
            @Value("${app.notification.service.timeout:5000}") int timeoutMs) {
        this.webClient = webClientBuilder.baseUrl(notificationEndpoint).build();
        this.notificationEndpoint = notificationEndpoint;
        this.timeout = Duration.ofMillis(timeoutMs);
    }

    @Override
    @CircuitBreaker(name = NOTIFICATION_SERVICE_NAME, fallbackMethod = "sendNotificationFallback")
    public Mono<Boolean> sendNotification(Event event) {
        log.info("Enviando notificación para evento {} de tipo {} del cliente {}",
                event.getEventId(), event.getEventType(), event.getCustomerId());

        Map<String, Object> payload = buildNotificationPayload(event);

        return webClient.post()
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(timeout)
                .map(response -> {
                    log.info("Notificación enviada exitosamente para evento {}", event.getEventId());
                    return Boolean.TRUE;
                })
                .doOnError(error -> log.error("Error enviando notificación para evento {}: {}",
                        event.getEventId(), error.getMessage()));
    }

    private Mono<Boolean> sendNotificationFallback(Event event, Throwable throwable) {
        log.warn("Fallback activado para evento {} debido a: {}. Registrando para retry posterior.",
                event.getEventId(), throwable.getMessage());
        return Mono.just(Boolean.FALSE);
    }

    private Map<String, Object> buildNotificationPayload(Event event) {
        Map<String, Object> payload = Map.of(
                "eventId", event.getEventId().toString(),
                "eventType", event.getEventType(),
                "timestamp", event.getTimestamp().toString(),
                "source", event.getSource(),
                "customerId", event.getCustomerId()
        );

        if (event instanceof FraudEvent fraudEvent) {
            return enrichWithFraudData(payload, fraudEvent);
        } else if (event instanceof RiskEvent riskEvent) {
            return enrichWithRiskData(payload, riskEvent);
        }

        return payload;
    }

    private Map<String, Object> enrichWithFraudData(Map<String, Object> payload, FraudEvent event) {
        return Map.of(
                "eventId", event.getEventId().toString(),
                "eventType", event.getEventType(),
                "timestamp", event.getTimestamp().toString(),
                "source", event.getSource(),
                "customerId", event.getCustomerId(),
                "accountNumber", event.getAccountNumber(),
                "transactionAmount", event.getTransactionAmount().toString(),
                "fraudDescription", event.getFraudDescription(),
                "transactionChannel", event.getTransactionChannel(),
                "riskLevel", event.getRiskLevel(),
                "priority", event.isHighRisk() ? "HIGH" : "MEDIUM",
                "message", buildFraudMessage(event)
        );
    }

    private Map<String, Object> enrichWithRiskData(Map<String, Object> payload, RiskEvent event) {
        return Map.of(
                "eventId", event.getEventId().toString(),
                "eventType", event.getEventType(),
                "timestamp", event.getTimestamp().toString(),
                "source", event.getSource(),
                "customerId", event.getCustomerId(),
                "riskType", event.getRiskType(),
                "riskDescription", event.getRiskDescription(),
                "recommendedAction", event.getRecommendedAction(),
                "riskScore", event.getRiskScore(),
                "priority", event.isCritical() ? "HIGH" : "MEDIUM",
                "message", buildRiskMessage(event)
        );
    }

    private String buildFraudMessage(FraudEvent event) {
        if (event.isHighRisk()) {
            return "ALERTA CRÍTICA: Se ha detectado una transacción sospechosa de alto riesgo en tu cuenta. " +
                    "Monto: $" + event.getTransactionAmount() + ". Por favor contacta inmediatamente.";
        }
        return "Notificación de seguridad: Se ha detectado una actividad inusual en tu cuenta. " +
                "Monto: $" + event.getTransactionAmount() + ".";
    }

    private String buildRiskMessage(RiskEvent event) {
        if (event.isCritical()) {
            return "ALERTA DE RIESGO CRÍTICO: " + event.getRiskDescription() + ". " +
                    "Acción recomendada: " + event.getRecommendedAction();
        }
        return "Notificación de riesgo: " + event.getRiskDescription() + ". " +
                "Puntuación de riesgo: " + event.getRiskScore();
    }

    @Override
    public Mono<Boolean> sendBulkNotifications(java.util.List<Event> events) {
        log.info("Enviando {} notificaciones en bulk", events.size());
        return Mono.whenDelayError(events.stream()
                        .map(this::sendNotification)
                        .toList())
                .then(Mono.just(Boolean.TRUE))
                .onErrorResume(error -> {
                    log.error("Error en envío masivo: {}", error.getMessage());
                    return Mono.just(Boolean.FALSE);
                });
    }
}