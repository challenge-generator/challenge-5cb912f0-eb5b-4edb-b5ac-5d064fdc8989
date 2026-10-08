package com.banca.notificacion.domain.port;

import com.banca.notificacion.domain.model.Event;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Puerto que define el contrato para el envío de notificaciones a los clientes.
 * Esta interfaz pertenece a la capa de dominio y es implementada por adaptadores
 * en la capa de infraestructura que utilizan diferentes canales de comunicación.
 */
public interface NotificationServicePort {

    /**
     * Envía una notificación a un cliente sobre un evento específico.
     * @param customerId Identificador del cliente
     * @param event El evento que motiva la notificación
     * @return Mono que emite true si el envío fue exitoso
     */
    Mono<Boolean> sendNotification(Long customerId, Event event);

    /**
     * Envía una notificación a múltiples clientes.
     * @param customerIds Lista de identificadores de clientes
     * @param event El evento que motiva la notificación
     * @return Mono que emite la cantidad de notificaciones enviadas exitosamente
     */
    Mono<Integer> sendBulkNotification(List<Long> customerIds, Event event);

    /**
     * Envía una notificación de alerta crítica con prioridad alta.
     * @param customerId Identificador del cliente
     * @param event El evento crítico
     * @return Mono que emite true si el envío fue exitoso
     */
    Mono<Boolean> sendCriticalAlert(Long customerId, Event event);

    /**
     * Verifica la disponibilidad del servicio de notificaciones.
     * @return Mono que emite true si el servicio está disponible
     */
    Mono<Boolean> isAvailable();

    /**
     * Obtiene el tipo de canal de notificación configurado.
     * @return String con el tipo de canal (EMAIL, SMS, PUSH, etc.)
     */
    String getChannelType();

    /**
     * Envía una notificación con retries automáticos en caso de fallo.
     * @param customerId Identificador del cliente
     * @param event El evento que motiva la notificación
     * @param maxRetries Número máximo de reintentos
     * @return Mono que emite true si el envío fue exitoso después de los reintentos
     */
    Mono<Boolean> sendNotificationWithRetry(Long customerId, Event event, int maxRetries);
}