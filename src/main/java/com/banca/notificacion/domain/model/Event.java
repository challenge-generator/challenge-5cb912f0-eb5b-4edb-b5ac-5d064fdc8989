package com.banca.notificacion.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Objects;

/**
 * Modelo base que representa un evento genérico en el sistema de notificaciones bancarias.
 * Todos los eventos deben extender esta clase para garantizar consistencia en atributos comunes
 * como idempotencia, timestamp y origen del evento.
 */
public abstract class Event {
    @NotNull(message = "El identificador del evento no puede ser nulo")
    private final UUID eventId;

    @NotBlank(message = "El tipo del evento no puede estar vacío")
    private final String eventType;

    @NotNull(message = "El timestamp del evento no puede ser nulo")
    @PastOrPresent(message = "El timestamp del evento no puede ser futuro")
    private final LocalDateTime timestamp;

    @NotBlank(message = "El origen del evento no puede estar vacío")
    private final String source;

    @NotNull(message = "El ID de cliente no puede ser nulo")
    @Positive(message = "El ID de cliente debe ser un valor positivo")
    private final Long customerId;

    protected Event(UUID eventId, String eventType, LocalDateTime timestamp, String source, Long customerId) {
        this.eventId = Objects.requireNonNull(eventId, "El eventId no puede ser nulo");
        this.eventType = Objects.requireNonNull(eventType, "El eventType no puede ser nulo").trim();
        this.timestamp = Objects.requireNonNull(timestamp, "El timestamp no puede ser nulo");
        this.source = Objects.requireNonNull(source, "El source no puede ser nulo").trim();
        this.customerId = Objects.requireNonNull(customerId, "El customerId no puede ser nulo");

        if (this.eventType.isBlank()) {
            throw new IllegalArgumentException("El tipo del evento no puede estar vacío");
        }
        if (this.source.isBlank()) {
            throw new IllegalArgumentException("El origen del evento no puede estar vacío");
        }
        if (this.customerId <= 0) {
            throw new IllegalArgumentException("El ID de cliente debe ser positivo");
        }
        if (this.timestamp.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("El timestamp no puede ser futuro");
        }
    }

    public UUID getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getSource() {
        return source;
    }

    public Long getCustomerId() {
        return customerId;
    }

    /**
     * Verifica si el evento es idempotente con respecto a otro evento.
     * Dos eventos son idempotentes si tienen el mismo eventId y eventType.
     *
     * @param other El otro evento a comparar
     * @return true si los eventos son idempotentes, false en caso contrario
     */
    public boolean isIdempotentWith(Event other) {
        if (other == null) {
            return false;
        }
        return this.eventId.equals(other.eventId) && this.eventType.equals(other.eventType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId.equals(event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventType='" + eventType + '\'' +
                ", timestamp=" + timestamp +
                ", source='" + source + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}