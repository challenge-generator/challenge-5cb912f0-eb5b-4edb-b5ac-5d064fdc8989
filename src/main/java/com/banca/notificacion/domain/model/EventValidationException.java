package com.banca.notificacion.domain.model;

import java.util.UUID;

public class EventValidationException extends RuntimeException {
    private final UUID eventId;
    private final String eventType;
    private final String fieldName;
    private final Object invalidValue;

    public EventValidationException(String message, UUID eventId, String eventType) {
        super(message);
        this.eventId = eventId;
        this.eventType = eventType;
        this.fieldName = null;
        this.invalidValue = null;
    }

    public EventValidationException(String message, UUID eventId, String eventType, 
                                     String fieldName, Object invalidValue) {
        super(message);
        this.eventId = eventId;
        this.eventType = eventType;
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    public UUID getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    public static EventValidationException missingRequiredField(UUID eventId, String eventType, String fieldName) {
        return new EventValidationException(
            "Campo requerido ausente: " + fieldName + " para evento de tipo " + eventType,
            eventId, eventType, fieldName, null
        );
    }

    public static EventValidationException invalidFieldValue(UUID eventId, String eventType, 
                                                              String fieldName, Object value) {
        return new EventValidationException(
            "Valor inválido para campo " + fieldName + ": " + value,
            eventId, eventType, fieldName, value
        );
    }

    public static EventValidationException invalidEventType(UUID eventId, String eventType) {
        return new EventValidationException(
            "Tipo de evento no reconocido: " + eventType,
            eventId, eventType, null, null
        );
    }
}