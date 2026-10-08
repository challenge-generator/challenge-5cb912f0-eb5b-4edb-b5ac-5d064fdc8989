package com.banca.notificacion.domain.port;

import com.banca.notificacion.domain.model.Event;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto que define el contrato para la persistencia de eventos del dominio.
 * Esta interfaz pertenece a la capa de dominio y es implementada por adaptadores
 * en la capa de infraestructura.
 */
public interface EventRepository {

    /**
     * Persiste un evento en el repositorio.
     * @param evento El evento a persistir
     * @return Mono que emite el evento persistido
     */
    Mono<Event> save(Event evento);

    /**
     * Busca un evento por su identificador único.
     * @param eventId El identificador del evento
     * @return Mono que emite el evento encontrado o vacío
     */
    Mono<Optional<Event>> findById(UUID eventId);

    /**
     * Busca eventos por el identificador del cliente.
     * @param customerId El identificador del cliente
     * @return Mono que emite la lista de eventos del cliente
     */
    Mono<List<Event>> findByCustomerId(Long customerId);

    /**
     * Busca eventos por tipo.
     * @param eventType El tipo de evento
     * @return Mono que emite la lista de eventos del tipo especificado
     */
    Mono<List<Event>> findByEventType(String eventType);

    /**
     * Verifica si existe un evento con el mismo identificador para garantizar idempotencia.
     * @param eventId El identificador del evento
     * @return Mono que emite true si existe, false en caso contrario
     */
    Mono<Boolean> existsById(UUID eventId);

    /**
     * Cuenta el número de eventos para un cliente específico.
     * @param customerId El identificador del cliente
     * @return Mono que emite la cantidad de eventos
     */
    Mono<Long> countByCustomerId(Long customerId);

    /**
     * Elimina eventos anteriores a una fecha determinada.
     * @param timestamp Fecha límite para la eliminación
     * @return Mono que emite la cantidad de eventos eliminados
     */
    Mono<Long> deleteEventsBefore(java.time.LocalDateTime timestamp);
}