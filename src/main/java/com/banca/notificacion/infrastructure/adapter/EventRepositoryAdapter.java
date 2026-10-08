package com.banca.notificacion.infrastructure.adapter;

import com.banca.notificacion.domain.model.Event;
import com.banca.notificacion.domain.model.FraudEvent;
import com.banca.notificacion.domain.model.RiskEvent;
import com.banca.notificacion.domain.port.EventRepository;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class EventRepositoryAdapter implements EventRepository {
    private static final Logger log = LoggerFactory.getLogger(EventRepositoryAdapter.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Mono<Event> save(Event event) {
        return Mono.fromCallable(() -> {
            if (event instanceof FraudEvent fraudEvent) {
                return persistFraudEvent(fraudEvent);
            } else if (event instanceof RiskEvent riskEvent) {
                return persistRiskEvent(riskEvent);
            }
            throw new IllegalArgumentException("Tipo de evento no soportado: " + event.getClass().getName());
        });
    }

    private Event persistFraudEvent(FraudEvent event) {
        FraudEventEntity entity = new FraudEventEntity();
        entity.setEventId(event.getEventId());
        entity.setEventType(event.getEventType());
        entity.setTimestamp(event.getTimestamp());
        entity.setSource(event.getSource());
        entity.setCustomerId(event.getCustomerId());
        entity.setAccountNumber(event.getAccountNumber());
        entity.setTransactionAmount(event.getTransactionAmount());
        entity.setFraudDescription(event.getFraudDescription());
        entity.setTransactionChannel(event.getTransactionChannel());
        entity.setRiskLevel(event.getRiskLevel());
        entity.setCreatedAt(LocalDateTime.now());

        entityManager.persist(entity);
        log.debug("Evento de fraude persistido: {}", event.getEventId());
        return event;
    }

    private Event persistRiskEvent(RiskEvent event) {
        RiskEventEntity entity = new RiskEventEntity();
        entity.setEventId(event.getEventId());
        entity.setEventType(event.getEventType());
        entity.setTimestamp(event.getTimestamp());
        entity.setSource(event.getSource());
        entity.setCustomerId(event.getCustomerId());
        entity.setRiskType(event.getRiskType());
        entity.setRiskDescription(event.getRiskDescription());
        entity.setRecommendedAction(event.getRecommendedAction());
        entity.setRiskScore(event.getRiskScore());
        entity.setCreatedAt(LocalDateTime.now());

        entityManager.persist(entity);
        log.debug("Evento de riesgo persistido: {}", event.getEventId());
        return event;
    }

    @Override
    public Mono<Boolean> existsByEventId(UUID eventId) {
        return Mono.fromCallable(() -> {
            Long count = entityManager.createQuery(
                "SELECT COUNT(e) FROM EventEntity e WHERE e.eventId = :eventId", Long.class)
                .setParameter("eventId", eventId)
                .getSingleResult();
            return count != null && count > 0;
        });
    }

    @Override
    public Mono<Event> findByEventId(UUID eventId) {
        return Mono.fromCallable(() -> {
            var query = entityManager.createQuery(
                "SELECT e FROM EventEntity e WHERE e.eventId = :eventId", EventEntity.class)
                .setParameter("eventId", eventId);
            
            var entities = query.getResultList();
            if (entities.isEmpty()) {
                return null;
            }
            return entities.get(0).toDomainEvent();
        });
    }

    @Entity
    @Table(name = "events")
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn(name = "event_type", discriminatorType = DiscriminatorType.STRING)
    public static abstract class EventEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private UUID eventId;

        @Column(nullable = false)
        private String eventType;

        @Column(nullable = false)
        private LocalDateTime timestamp;

        @Column(nullable = false)
        private String source;

        @Column(nullable = false)
        private Long customerId;

        @Column(nullable = false)
        private LocalDateTime createdAt;

        public abstract Event toDomainEvent();

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public UUID getEventId() { return eventId; }
        public void setEventId(UUID eventId) { this.eventId = eventId; }
        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }

    @Entity
    @DiscriminatorValue("FRAUD")
    public static class FraudEventEntity extends EventEntity {
        @Column(nullable = false)
        private String accountNumber;

        @Column(nullable = false, precision = 19, scale = 4)
        private BigDecimal transactionAmount;

        @Column(length = 1000)
        private String fraudDescription;

        @Column
        private String transactionChannel;

        @Column(nullable = false)
        private int riskLevel;

        @Override
        public Event toDomainEvent() {
            return new FraudEvent(
                getEventId(),
                getTimestamp(),
                getSource(),
                getCustomerId(),
                accountNumber,
                transactionAmount,
                fraudDescription,
                transactionChannel,
                riskLevel
            );
        }

        public String getAccountNumber() { return accountNumber; }
        public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
        public BigDecimal getTransactionAmount() { return transactionAmount; }
        public void setTransactionAmount(BigDecimal transactionAmount) { this.transactionAmount = transactionAmount; }
        public String getFraudDescription() { return fraudDescription; }
        public void setFraudDescription(String fraudDescription) { this.fraudDescription = fraudDescription; }
        public String getTransactionChannel() { return transactionChannel; }
        public void setTransactionChannel(String transactionChannel) { this.transactionChannel = transactionChannel; }
        public int getRiskLevel() { return riskLevel; }
        public void setRiskLevel(int riskLevel) { this.riskLevel = riskLevel; }
    }

    @Entity
    @DiscriminatorValue("RISK")
    public static class RiskEventEntity extends EventEntity {
        @Column(nullable = false)
        private String riskType;

        @Column(length = 1000)
        private String riskDescription;

        @Column(length = 500)
        private String recommendedAction;

        @Column(nullable = false)
        private int riskScore;

        @Override
        public Event toDomainEvent() {
            return new RiskEvent(
                getEventId(),
                getTimestamp(),
                getSource(),
                getCustomerId(),
                riskType,
                riskDescription,
                recommendedAction,
                riskScore
            );
        }

        public String getRiskType() { return riskType; }
        public void setRiskType(String riskType) { this.riskType = riskType; }
        public String getRiskDescription() { return riskDescription; }
        public void setRiskDescription(String riskDescription) { this.riskDescription = riskDescription; }
        public String getRecommendedAction() { return recommendedAction; }
        public void setRecommendedAction(String recommendedAction) { this.recommendedAction = recommendedAction; }
        public int getRiskScore() { return riskScore; }
        public void setRiskScore(int riskScore) { this.riskScore = riskScore; }
    }
}