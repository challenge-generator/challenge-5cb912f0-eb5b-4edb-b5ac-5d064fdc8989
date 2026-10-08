package com.banca.notificacion.application;

import com.banca.notificacion.domain.model.Event;
import com.banca.notificacion.domain.model.FraudEvent;
import com.banca.notificacion.domain.model.RiskEvent;
import com.banca.notificacion.domain.model.EventValidationException;
import com.banca.notificacion.domain.port.EventRepository;
import com.banca.notificacion.domain.port.NotificationServicePort;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventProcessorService - Tests Unitarios")
class EventProcessorServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private NotificationServicePort notificationServicePort;

    private EventProcessorService eventProcessorService;

    @BeforeEach
    void setUp() {
        eventProcessorService = new EventProcessorService(eventRepository, notificationServicePort);
    }

    @Nested
    @DisplayName("Procesamiento de FraudEvent")
    class ProcessFraudEvent {

        @Test
        @DisplayName("Debe procesar correctamente un evento de fraude de alto riesgo")
        void debeProcesarEventoFraudeAltoRiesgo() {
            FraudEvent fraudEvent = new FraudEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    "ANTIFRAUD",
                    12345L,
                    "4532****1234",
                    new BigDecimal("50000.00"),
                    "Transacción sospechosa detectada en horario no habitual",
                    "APP_MOVIL",
                    85
            );

            when(eventRepository.existsByEventId(fraudEvent.getEventId())).thenReturn(Mono.just(false));
            when(eventRepository.save(any(Event.class))).thenReturn(Mono.just(fraudEvent));
            when(notificationServicePort.sendNotification(eq(12345L), any(String.class)))
                    .thenReturn(Mono.just(true));

            StepVerifier.create(eventProcessorService.processEvent(fraudEvent))
                    .expectNext(true)
                    .verifyComplete();

            verify(eventRepository).existsByEventId(fraudEvent.getEventId());
            verify(eventRepository).save(fraudEvent);
            verify(notificationServicePort).sendNotification(eq(12345L), any(String.class));
        }

        @Test
        @DisplayName("Debe rechazar evento de fraude duplicado por idempotencia")
        void debeRechazarEventoDuplicado() {
            FraudEvent fraudEvent = new FraudEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    "ANTIFRAUD",
                    12345L,
                    "4532****1234",
                    new BigDecimal("50000.00"),
                    "Transacción sospechosa",
                    "APP_MOVIL",
                    85
            );

            when(eventRepository.existsByEventId(fraudEvent.getEventId())).thenReturn(Mono.just(true));

            StepVerifier.create(eventProcessorService.processEvent(fraudEvent))
                    .expectNext(false)
                    .verifyComplete();

            verify(eventRepository, never()).save(any(Event.class));
            verify(notificationServicePort, never()).sendNotification(any(), any());
        }

        @Test
        @DisplayName("Debe omitir notificación para evento de fraude de bajo riesgo")
        void debeOmitirNotificacionBajoRiesgo() {
            FraudEvent fraudEvent = new FraudEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    "ANTIFRAUD",
                    12345L,
                    "4532****1234",
                    new BigDecimal("100.00"),
                    "Transacción menor",
                    "APP_MOVIL",
                    25
            );

            when(eventRepository.existsByEventId(fraudEvent.getEventId())).thenReturn(Mono.just(false));
            when(eventRepository.save(any(Event.class))).thenReturn(Mono.just(fraudEvent));

            StepVerifier.create(eventProcessorService.processEvent(fraudEvent))
                    .expectNext(true)
                    .verifyComplete();

            verify(notificationServicePort, never()).sendNotification(any(), any());
        }
    }

    @Nested
    @DisplayName("Procesamiento de RiskEvent")
    class ProcessRiskEvent {

        @Test
        @DisplayName("Debe procesar evento de riesgo crítico y enviar notificación inmediata")
        void debeProcesarEventoRiesgoCritico() {
            RiskEvent riskEvent = new RiskEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    "BUREAU",
                    67890L,
                    "SCORE_BAJO",
                    "Puntuación de riesgo crediticio por debajo del umbral permitido",
                    "Revisar manualmente la solicitud de crédito",
                    92
            );

            when(eventRepository.existsByEventId(riskEvent.getEventId())).thenReturn(Mono.just(false));
            when(eventRepository.save(any(Event.class))).thenReturn(Mono.just(riskEvent));
            when(notificationServicePort.sendNotification(eq(67890L), any(String.class)))
                    .thenReturn(Mono.just(true));

            StepVerifier.create(eventProcessorService.processEvent(riskEvent))
                    .expectNext(true)
                    .verifyComplete();

            verify(notificationServicePort).sendNotification(eq(67890L), any(String.class));
        }

        @Test
        @DisplayName("Debe procesar evento de riesgo no crítico sin notificación inmediata")
        void debeProcesarEventoRiesgoNoCritico() {
            RiskEvent riskEvent = new RiskEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    "BUREAU",
                    67890L,
                    "CONCENTRACION",
                    "Alta concentración en un tipo de producto",
                    "Monitorear evolución",
                    45
            );

            when(eventRepository.existsByEventId(riskEvent.getEventId())).thenReturn(Mono.just(false));
            when(eventRepository.save(any(Event.class))).thenReturn(Mono.just(riskEvent));

            StepVerifier.create(eventProcessorService.processEvent(riskEvent))
                    .expectNext(true)
                    .verifyComplete();

            verify(notificationServicePort, never()).sendNotification(any(), any());
        }
    }

    @Nested
    @DisplayName("Validación de eventos")
    class EventValidation {

        @Test
        @DisplayName("Debe lanzar excepción para evento nulo")
        void debeLanzarExcepcionParaEventoNulo() {
            assertThatThrownBy(() -> eventProcessorService.processEvent(null))
                    .isInstanceOf(EventValidationException.class)
                    .hasMessageContaining("no puede ser nulo");
        }

        @Test
        @DisplayName("Debe validar que el evento tiene customerId")
        void debeValidarCustomerId() {
            FraudEvent fraudEvent = new FraudEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    "ANTIFRAUD",
                    null,
                    "4532****1234",
                    new BigDecimal("50000.00"),
                    "Transacción",
                    "APP_MOVIL",
                    85
            );

            StepVerifier.create(eventProcessorService.processEvent(fraudEvent))
                    .expectError(EventValidationException.class)
                    .verify();
        }
    }

    @Nested
    @DisplayName("Manejo de errores de infraestructura")
    class InfrastructureErrors {

        @Test
        @DisplayName("Debe manejar error al guardar evento en repositorio")
        void debeManejarErrorGuardadoRepositorio() {
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

            when(eventRepository.existsByEventId(fraudEvent.getEventId())).thenReturn(Mono.just(false));
            when(eventRepository.save(any(Event.class)))
                    .thenReturn(Mono.error(new RuntimeException("Error de base de datos")));

            StepVerifier.create(eventProcessorService.processEvent(fraudEvent))
                    .expectError(RuntimeException.class)
                    .verify();
        }

        @Test
        @DisplayName("Debe manejar falla en servicio de notificación")
        void debeManejarFallaNotificacion() {
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

            when(eventRepository.existsByEventId(fraudEvent.getEventId())).thenReturn(Mono.just(false));
            when(eventRepository.save(any(Event.class))).thenReturn(Mono.just(fraudEvent));
            when(notificationServicePort.sendNotification(any(Long.class), any(String.class)))
                    .thenReturn(Mono.error(new RuntimeException("Servicio de notificaciones no disponible")));

            StepVerifier.create(eventProcessorService.processEvent(fraudEvent))
                    .expectNext(true)
                    .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Verificación de idempotencia")
    class IdempotencyVerification {

        @Test
        @DisplayName("Debe verificar idempotencia correctamente")
        void debeVerificarIdempotencia() {
            UUID eventId = UUID.randomUUID();
            FraudEvent event1 = new FraudEvent(
                    eventId,
                    LocalDateTime.now(),
                    "ANTIFRAUD",
                    12345L,
                    "4532****1234",
                    new BigDecimal("50000.00"),
                    "Transacción",
                    "APP_MOVIL",
                    85
            );
            FraudEvent event2 = new FraudEvent(
                    eventId,
                    LocalDateTime.now().plusHours(1),
                    "ANTIFRAUD",
                    12345L,
                    "4532****1234",
                    new BigDecimal("50000.00"),
                    "Transacción",
                    "APP_MOVIL",
                    85
            );

            assertThat(event1.isIdempotentWith(event2)).isTrue();
        }

        @Test
        @DisplayName("Debe detectar eventos no idempotentes")
        void debeDetectarEventosNoIdempotentes() {
            FraudEvent event1 = new FraudEvent(
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
            FraudEvent event2 = new FraudEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    "ANTIFRAUD",
                    12345L,
                    "4532****5678",
                    new BigDecimal("99999.00"),
                    "Otra transacción",
                    "APP_WEB",
                    90
            );

            assertThat(event1.isIdempotentWith(event2)).isFalse();
        }
    }
}