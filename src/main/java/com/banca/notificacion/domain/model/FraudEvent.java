package com.banca.notificacion.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Objects;

/**
 * Evento que representa una alerta de fraude detectada en una transacción bancaria.
 * Contiene información específica sobre la transacción sospechosa y el nivel de riesgo asociado.
 */
public final class FraudEvent extends Event {
    @NotBlank(message = "El número de cuenta no puede estar vacío")
    private final String accountNumber;

    @NotNull(message = "El monto de la transacción no puede ser nulo")
    @Positive(message = "El monto de la transacción debe ser positivo")
    private final BigDecimal transactionAmount;

    @NotBlank(message = "La descripción del fraude no puede estar vacía")
    private final String fraudDescription;

    @NotBlank(message = "El canal de la transacción no puede estar vacío")
    private final String transactionChannel;

    @Positive(message = "El nivel de riesgo debe ser un valor positivo")
    private final int riskLevel;

    public FraudEvent(UUID eventId, LocalDateTime timestamp, String source, Long customerId,
                      String accountNumber, BigDecimal transactionAmount,
                      String fraudDescription, String transactionChannel, int riskLevel) {
        super(eventId, "FRAUD_ALERT", timestamp, source, customerId);
        this.accountNumber = Objects.requireNonNull(accountNumber, "El accountNumber no puede ser nulo").trim();
        this.transactionAmount = Objects.requireNonNull(transactionAmount, "El transactionAmount no puede ser nulo");
        this.fraudDescription = Objects.requireNonNull(fraudDescription, "La fraudDescription no puede ser nula").trim();
        this.transactionChannel = Objects.requireNonNull(transactionChannel, "El transactionChannel no puede ser nulo").trim();
        this.riskLevel = riskLevel;

        if (this.accountNumber.isBlank()) {
            throw new IllegalArgumentException("El número de cuenta no puede estar vacío");
        }
        if (this.fraudDescription.isBlank()) {
            throw new IllegalArgumentException("La descripción del fraude no puede estar vacía");
        }
        if (this.transactionChannel.isBlank()) {
            throw new IllegalArgumentException("El canal de la transacción no puede estar vacío");
        }
        if (this.transactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de la transacción debe ser positivo");
        }
        if (this.riskLevel <= 0) {
            throw new IllegalArgumentException("El nivel de riesgo debe ser positivo");
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public String getFraudDescription() {
        return fraudDescription;
    }

    public String getTransactionChannel() {
        return transactionChannel;
    }

    public int getRiskLevel() {
        return riskLevel;
    }

    /**
     * Determina si el fraude es de alto riesgo basado en el nivel de riesgo.
     *
     * @return true si el nivel de riesgo es 8 o superior, false en caso contrario
     */
    public boolean isHighRisk() {
        return riskLevel >= 8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FraudEvent that = (FraudEvent) o;
        return riskLevel == that.riskLevel &&
                accountNumber.equals(that.accountNumber) &&
                transactionAmount.equals(that.transactionAmount) &&
                fraudDescription.equals(that.fraudDescription) &&
                transactionChannel.equals(that.transactionChannel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountNumber, transactionAmount, fraudDescription, transactionChannel, riskLevel);
    }

    @Override
    public String toString() {
        return "FraudEvent{" +
                "accountNumber='" + accountNumber + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", fraudDescription='" + fraudDescription + '\'' +
                ", transactionChannel='" + transactionChannel + '\'' +
                ", riskLevel=" + riskLevel +
                "} " + super.toString();
    }
}