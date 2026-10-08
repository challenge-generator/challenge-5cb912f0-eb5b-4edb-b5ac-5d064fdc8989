package com.banca.notificacion.infrastructure.adapter;

import com.banca.notificacion.domain.model.FraudEvent;
import com.banca.notificacion.domain.model.RiskEvent;
import com.banca.notificacion.infrastructure.adapter.EventRepositoryAdapter;
import com.banca.notificacion.infrastructure.entity.EventEntity;
import com.banca.notificacion.infrastructure.repository.EventJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventRepositoryAdapter - Tests Unitarios")
class EventRepositoryAdapterTest {

    @Mock
    private EventJpaRepository eventJpaRepository;

    private EventRepositoryAdapter eventRepositoryAdapter;

    @BeforeEach
    void setUp() {
        eventRepositoryAdapter = new EventRepositoryAdapter(eventJpaRepository);
    }

    @Nested
    @DisplayName("Operaciones de guardado")
    class SaveOperations {

        @Test
        @DisplayName("Debe guardar correctamente un FraudEvent")
        void debeGuardarFraudEvent() {
            FraudEvent fraudEvent = new FraudEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    "ANTIFRAUD",
                    12345L,
                    "4532****1234",
                    new BigDecimal("50000.00"),
                    "Transacción sospechosa en horario no habitual",
                    "APP_MOVIL",
                    85
            );

            EventEntity entityToSave = EventEntity.fromDomain(fraudEvent);
            EventEntity savedEntity = new EventEntity();
            savedEntity.setId(1L);
            savedEntity.setEventId(fraudEvent.getEventId());
            savedEntity.setEventType(fraudEvent.getEventType());
            savedEntity.setTimestamp(fraudEvent.getTimestamp());
            savedEntity.setSource(fraudEvent.getSource());
            savedEntity.setCustomerId(fraudEvent.getCustomerId());
            savedEntity.setPayload("{\"accountNumber\":\"4532****1234\"}");

            when(eventJpaRepository.save(any(EventEntity.class))).thenReturn(Mono.just(savedEntity));

            StepVerifier.create(eventRepositoryAdapter.save(fraudEvent))
                    .assertNext(savedEvent -> {
                        assertThat(savedEvent.getEventId()).isEqualTo(fraudEvent.getEventId());
                        assertThat(savedEvent.getEventType()).isEqualTo(fraudEvent.getEventType());
                    })
                    .verifyComplete();

            verify(eventJpaRepository).save(any(EventEntity.class));
        }

        @Test
        @DisplayName("Debe guardar correctamente un RiskEvent")
        void debeGuardarRiskEvent() {
            RiskEvent riskEvent = new RiskEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    "BUREAU",
                    67890L,
                    "SCORE_BAJO",
                    "Puntuación de riesgo crediticio baja",
                    "Revisar manualmente",
                    92
            );

            EventEntity savedEntity = new EventEntity();
            savedEntity.setId(1L);
            savedEntity.setEventId(riskEvent.getEventId());
            savedEntity.setEventType(riskEvent.getEventType());
            savedEntity.setTimestamp(riskEvent.getTimestamp());
            savedEntity.setSource(riskEvent.getSource());
            savedEntity.setCustomerId(riskEvent.getCustomerId());
            savedEntity.setPayload("{\"riskType\":\"SCORE_BAJO\"}");

            when(eventJpaRepository.save(any(EventEntity.class))).thenReturn(Mono.just(savedEntity));

            StepVerifier.create(eventRepositoryAdapter.save(riskEvent))
                    .assertNext(savedEvent -> {
                        assertThat(savedEvent.getEventId()).isEqualTo(riskEvent.getEventId());
                        assertThat(savedEvent.getCustomerId()).isEqualTo(riskEvent.getCustomerId());
                    })
                    .verifyComplete();

            verify(eventJpaRepository).save(any(EventEntity.class));
        }

        @Test
        @DisplayName("Debe manejar error al guardar evento")
        void debeManejarErrorGuardado() {
            FraudEvent fraudEvent = new FraudEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    "ANTIFRAUD",
                    12345L,
                    "4532****1234",
                    new BigDecimal("50000.00"),
                    "Transacción",
                    "APP_MOVIL",
                    85
            );

            when(eventJpaRepository.save(any(EventEntity.class)))
                    .thenReturn(Mono.error(new RuntimeException("Error de base de datos")));

            StepVerifier.create(eventRepositoryAdapter.save(fraudEvent))
                    .expectError(RuntimeException.class)
                    .verify();
        }
    }

    @Nested
    @DisplayName("Operaciones de verificación de existencia")
    class ExistsOperations {

        @Test
        @DisplayName("Debe retornar true cuando el evento existe")
        void debeRetornarTrueSiExiste() {
            UUID eventId = UUID.randomUUID();
            when(eventJpaRepository.existsByEventId(eventId)).thenReturn(Mono.just(true));

            StepVerifier.create(eventRepositoryAdapter.existsByEventId(eventId))
                    .expectNext(true)
                    .verifyComplete();

            verify(eventJpaRepository).existsByEventId(eventId);
        }

        @Test
        @DisplayName("Debe retornar false cuando el evento no existe")
        void debeRetornarFalseSiNoExiste() {
            UUID eventId = UUID.randomUUID();
            when(eventJpaRepository.existsByEventId(eventId)).thenReturn(Mono.just(false));

            StepVerifier.create(eventRepositoryAdapter.existsByEventId(eventId))
                    .expectNext(false)
                    .verifyComplete();

            verify(eventJpaRepository).existsByEventId(eventId);
        }

        @Test
        @DisplayName("Debe manejar error al verificar existencia")
        void debeManejarErrorVerificacion() {
            UUID eventId = UUID.randomUUID();
            when(eventJpaRepository.existsByEventId(eventId))
                    .thenReturn(Mono.error(new RuntimeException("Error de conexión")));

            StepVerifier.create(eventRepositoryAdapter.existsByEventId(eventId))
                    .expectError(RuntimeException.class)
                    .verify();
        }
    }

    @Nested
    @DisplayName("Operaciones de búsqueda")
    class FindOperations {

        @Test
        @DisplayName("Debe buscar evento por ID")
        void debeBuscarEventoPorId() {
            UUID eventId = UUID.randomUUID();
            EventEntity entity = new EventEntity();
            entity.setId(1L);
            entity.setEventId(eventId);
            entity.setEventType("FRAUD");
            entity.setTimestamp(LocalDateTime.now());
            entity.setSource("ANTIFRAUD");
            entity.setCustomerId(12345L);
            entity.setPayload("{\"accountNumber\":\"4532****1234\"}");

            when(eventJpaRepository.findByEventId(eventId)).thenReturn(Mono.just(Optional.of(entity)));

            StepVerifier.create(eventRepositoryAdapter.findByEventId(eventId))
                    .assertNext(foundEvent -> {
                        assertThat(foundEvent).isPresent();
                        assertThat(foundEvent.get().getEventId()).isEqualTo(eventId);
                    })
                    .verifyComplete();

            verify(eventJpaRepository).findByEventId(eventId);
        }

        @Test
        @DisplayName("Debe retornar empty cuando no encuentra evento")
        void debeRetornarEmptySiNoEncuentra() {
            UUID eventId = UUID.randomUUID();
            when(eventJpaRepository.findByEventId(eventId)).thenReturn(Mono.just(Optional.empty()));

            StepVerifier.create(eventRepositoryAdapter.findByEventId(eventId))
                    .assertNext(foundEvent -> assertThat(foundEvent).isEmpty())
                    .verifyComplete();
        }

        @Test
        @DisplayName("Debe buscar eventos por customerId")
        void debeBuscarEventosPorCustomerId() {
            Long customerId = 12345L;
            EventEntity entity = new EventEntity();
            entity.setId(1L);
            entity.setEventId(UUID.randomUUID());
            entity.setEventType("FRAUD");
            entity.setTimestamp(LocalDateTime.now());
            entity.setSource("ANTIFRAUD");
            entity.setCustomerId(customerId);
            entity.setPayload("{\"accountNumber\":\"4532****1234\"}");

            when(eventJpaRepository.findByCustomerId(customerId)).thenReturn(Mono.just(java.util.List.of(entity)));

            StepVerifier.create(eventRepositoryAdapter.findByCustomerId(customerId))
                    .assertNext(events -> {
                        assertThat(events).hasSize(1);
                        assertThat(events.get(0).getCustomerId()).isEqualTo(customerId);
                    })
                    .verifyComplete();

            verify(eventJpaRepository).findByCustomerId(customerId);
        }
    }

    @Nested
    @DisplayName("Operaciones de eliminación")
    class DeleteOperations {

        @Test
        @DisplayName("Debe eliminar evento por ID")
        void debeEliminarEventoPorId() {
            UUID eventId = UUID.randomUUID();
            when(eventJpaRepository.deleteByEventId(eventId)).thenReturn(Mono.just(true));

            StepVerifier.create(eventRepositoryAdapter.deleteByEventId(eventId))
                    .expectNext(true)
                    .verifyComplete();

            verify(eventJpaRepository).deleteByEventId(eventId);
        }

        @Test
        @DisplayName("Debe manejar error al eliminar evento")
        void debeManejarErrorEliminacion() {
            UUID eventId = UUID.randomUUID();
            when(eventJpaRepository.deleteByEventId(eventId))
                    .thenReturn(Mono.error(new RuntimeException("Error de eliminación")));

            StepVerifier.create(eventRepositoryAdapter.deleteByEventId(eventId))
                    .expectError(RuntimeException.class)
                    .verify();
        }
    }
}