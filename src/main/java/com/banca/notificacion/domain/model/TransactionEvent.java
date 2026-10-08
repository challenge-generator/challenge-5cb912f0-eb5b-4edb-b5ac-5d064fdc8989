package com.banca.notificacion.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Evento de transacción que representa una operación financiera realizada por un cliente.
 * Incluye información de la cuenta origen, destino, monto y estado de la transacción.
 */
public final class TransactionEvent extends Event {

    private final String accountNumber;
    private final String destinationAccountNumber;
    private final BigDecimal amount;
    private final String currency;
    private final String transactionType;
    private final String transactionStatus;
    private final String merchantName;
    private final String location;
    private final boolean isInternational;

    public TransactionEvent(UUID eventId, LocalDateTime timestamp, String source, Long customerId,
                           String accountNumber, String destinationAccountNumber, BigDecimal amount,
                           String currency, String transactionType, String transactionStatus,
                           String merchantName, String location, boolean isInternational) {
        super(eventId, "TRANSACTION", timestamp, source, customerId);
        this.accountNumber = validateAccountNumber(accountNumber);
        this.destinationAccountNumber = validateAccountNumber(destinationAccountNumber);
        this.amount = validateAmount(amount);
        this.currency = validateCurrency(currency);
        this.transactionType = validateTransactionType(transactionType);
        this.transactionStatus = validateTransactionStatus(transactionStatus);
        this.merchantName = merchantName;
        this.location = location;
        this.isInternational = isInternational;
    }

    private String validateAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new EventValidationException("El número de cuenta no puede ser nulo o vacío");
        }
        if (accountNumber.length() < 10 || accountNumber.length() > 34) {
            throw new EventValidationException("El número de cuenta debe tener entre 10 y 34 caracteres");
        }
        return accountNumber;
    }

    private BigDecimal validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new EventValidationException("El monto de la transacción no puede ser nulo");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new EventValidationException("El monto de la transacción debe ser mayor que cero");
        }
        if (amount.compareTo(new BigDecimal("999999999.99")) > 0) {
            throw new EventValidationException("El monto de la transacción excede el límite permitido");
        }
        return amount;
    }

    private String validateCurrency(String currency) {
        if (currency == null || currency.isBlank()) {
            throw new EventValidationException("La moneda no puede ser nula o vacía");
        }
        String upperCurrency = currency.toUpperCase();
        if (!upperCurrency.matches("^[A-Z]{3}$")) {
            throw new EventValidationException("La moneda debe ser un código ISO 4217 de 3 letras");
        }
        return upperCurrency;
    }

    private String validateTransactionType(String transactionType) {
        if (transactionType == null || transactionType.isBlank()) {
            throw new EventValidationException("El tipo de transacción no puede ser nulo o vacío");
        }
        return transactionType;
    }

    private String validateTransactionStatus(String transactionStatus) {
        if (transactionStatus == null || transactionStatus.isBlank()) {
            throw new EventValidationException("El estado de la transacción no puede ser nulo o vacío");
        }
        return transactionStatus;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getLocation() {
        return location;
    }

    public boolean isInternational() {
        return isInternational;
    }

    public boolean isHighValue() {
        return amount.compareTo(new BigDecimal("10000.00")) >= 0;
    }

    public boolean isPending() {
        return "PENDING".equalsIgnoreCase(transactionStatus);
    }

    public boolean isCompleted() {
        return "COMPLETED".equalsIgnoreCase(transactionStatus);
    }

    public boolean isFailed() {
        return "FAILED".equalsIgnoreCase(transactionStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TransactionEvent that = (TransactionEvent) o;
        return isInternational == that.isInternational &&
               Objects.equals(accountNumber, that.accountNumber) &&
               Objects.equals(destinationAccountNumber, that.destinationAccountNumber) &&
               Objects.equals(amount, that.amount) &&
               Objects.equals(transactionType, that.transactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountNumber, destinationAccountNumber, amount, 
                           transactionType, isInternational);
    }

    @Override
    public String toString() {
        return "TransactionEvent{" +
               "eventId=" + getEventId() +
               ", eventType='" + getEventType() + '\'' +
               ", timestamp=" + getTimestamp() +
               ", source='" + getSource() + '\'' +
               ", customerId=" + getCustomerId() +
               ", accountNumber='" + accountNumber + '\'' +
               ", destinationAccountNumber='" + destinationAccountNumber + '\'' +
               ", amount=" + amount +
               ", currency='" + currency + '\'' +
               ", transactionType='" + transactionType + '\'' +
               ", transactionStatus='" + transactionStatus + '\'' +
               ", merchantName='" + merchantName + '\'' +
               ", location='" + location + '\'' +
               ", isInternational=" + isInternational +
               '}';
    }
}