package com.banca.notificacion.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Objects;

/**
 * Evento que representa una alerta de riesgo asociada a un cliente o cuenta bancaria.
 * Contiene información sobre el tipo de riesgo detectado y las acciones recomendadas.
 */
public final class RiskEvent extends Event {
    @NotBlank(message = "El tipo de riesgo no puede estar vacío")
    private final String riskType;

    @NotBlank(message = "La descripción del riesgo no puede estar vacía")
    private final String riskDescription;

    @NotBlank(message = "La acción recomendada no puede estar vacía")
    private final String recommendedAction;

    @Positive(message = "El score de riesgo debe ser un valor positivo")
    private final int riskScore;

    public RiskEvent(UUID eventId, LocalDateTime timestamp, String source, Long customerId,
                     String riskType, String riskDescription, String recommendedAction, int riskScore) {
        super(eventId, "RISK_ALERT", timestamp, source, customerId);
        this.riskType = Objects.requireNonNull(riskType, "El riskType no puede ser nulo").trim();
        this.riskDescription = Objects.requireNonNull(riskDescription, "La riskDescription no puede ser nula").trim();
        this.recommendedAction = Objects.requireNonNull(recommendedAction, "La recommendedAction no puede ser nula").trim();
        this.riskScore = riskScore;

        if (this.riskType.isBlank()) {
            throw new IllegalArgumentException("El tipo de riesgo no puede estar vacío");
        }
        if (this.riskDescription.isBlank()) {
            throw new IllegalArgumentException("La descripción del riesgo no puede estar vacía");
        }
        if (this.recommendedAction.isBlank()) {
            throw new IllegalArgumentException("La acción recomendada no puede estar vacía");
        }
        if (this.riskScore <= 0) {
            throw new IllegalArgumentException("El score de riesgo debe ser positivo");
        }
    }

    public String getRiskType() {
        return riskType;
    }

    public String getRiskDescription() {
        return riskDescription;
    }

    public String getRecommendedAction() {
        return recommendedAction;
    }

    public int getRiskScore() {
        return riskScore;
    }

    /**
     * Determina si el riesgo es crítico basado en el score de riesgo.
     *
     * @return true si el score de riesgo es 70 o superior, false en caso contrario
     */
    public boolean isCritical() {
        return riskScore >= 70;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RiskEvent riskEvent = (RiskEvent) o;
        return riskScore == riskEvent.riskScore &&
                riskType.equals(riskEvent.riskType) &&
                riskDescription.equals(riskEvent.riskDescription) &&
                recommendedAction.equals(riskEvent.recommendedAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), riskType, riskDescription, recommendedAction, riskScore);
    }

    @Override
    public String toString() {
        return "RiskEvent{" +
                "riskType='" + riskType + '\'' +
                ", riskDescription='" + riskDescription + '\'' +
                ", recommendedAction='" + recommendedAction + '\'' +
                ", riskScore=" + riskScore +
                "} " + super.toString();
    }
}