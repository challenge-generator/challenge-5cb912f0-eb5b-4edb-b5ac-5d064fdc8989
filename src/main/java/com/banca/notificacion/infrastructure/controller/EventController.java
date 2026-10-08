package com.banca.notificacion.infrastructure.controller;

import com.banca.notificacion.application.EventProcessorService;
import com.banca.notificacion.domain.model.Event;
import com.banca.notificacion.domain.model.EventValidationException;
import com.banca.notificacion.domain.model.FraudEvent;
import com.banca.notificacion.domain.model.RiskEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@Tag(name = "Event Management", description = "API para recepción y procesamiento de eventos del sistema bancario")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    private final EventProcessorService eventProcessorService;

    public EventController(EventProcessorService eventProcessorService) {
        this.eventProcessorService = eventProcessorService;
    }

    @PostMapping("/fraud")
    @Operation(summary = "Recibir evento de fraude", description = "Recibe y procesa un evento de fraude del motor antifraude")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Evento aceptado para procesamiento"),
            @ApiResponse(responseCode = "400", description = "Datos del evento inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Evento duplicado (idempotencia)", content = @Content),
            @ApiResponse(responseCode = "503", description = "Error temporal en el procesamiento", content = @Content)
    })
    public Mono<ResponseEntity<Map<String, Object>>> receiveFraudEvent(
            @Parameter(description = "Datos del evento de fraude", required = true,
                    schema = @Schema(implementation = FraudEventRequest.class))
            @RequestBody FraudEventRequest request) {

        log.info("Recibido evento de fraude del source: {}, customerId: {}", request.source(), request.customerId());

        return Mono.fromCallable(() -> validateFraudRequest(request))
                .flatMap(validRequest -> {
                    FraudEvent event = buildFraudEvent(validRequest);
                    return eventProcessorService.processEvent(event)
                            .map(result -> ResponseEntity.status(HttpStatus.ACCEPTED)
                                    .body(Map.of(
                                            "status", "ACCEPTED",
                                            "eventId", event.getEventId().toString(),
                                            "message", "Evento de fraude aceptado para procesamiento",
                                            "timestamp", LocalDateTime.now().toString()
                                    )))
                            .onErrorResume(EventValidationException.class, e ->
                                    Mono.just(ResponseEntity.badRequest()
                                            .body(Map.of(
                                                    "status", "ERROR",
                                                    "error", e.getClass().getSimpleName(),
                                                    "message", e.getMessage(),
                                                    "timestamp", LocalDateTime.now().toString()
                                            ))));
                });
    }

    @PostMapping("/risk")
    @Operation(summary = "Recibir evento de riesgo", description = "Recibe y procesa un evento de riesgo del buró de riesgos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Evento aceptado para procesamiento"),
            @ApiResponse(responseCode = "400", description = "Datos del evento inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Evento duplicado (idempotencia)", content = @Content),
            @ApiResponse(responseCode = "503", description = "Error temporal en el procesamiento", content = @Content)
    })
    public Mono<ResponseEntity<Map<String, Object>>> receiveRiskEvent(
            @Parameter(description = "Datos del evento de riesgo", required = true,
                    schema = @Schema(implementation = RiskEventRequest.class))
            @RequestBody RiskEventRequest request) {

        log.info("Recibido evento de riesgo del source: {}, customerId: {}", request.source(), request.customerId());

        return Mono.fromCallable(() -> validateRiskRequest(request))
                .flatMap(validRequest -> {
                    RiskEvent event = buildRiskEvent(validRequest);
                    return eventProcessorService.processEvent(event)
                            .map(result -> ResponseEntity.status(HttpStatus.ACCEPTED)
                                    .body(Map.of(
                                            "status", "ACCEPTED",
                                            "eventId", event.getEventId().toString(),
                                            "message", "Evento de riesgo aceptado para procesamiento",
                                            "timestamp", LocalDateTime.now().toString()
                                    )))
                            .onErrorResume(EventValidationException.class, e ->
                                    Mono.just(ResponseEntity.badRequest()
                                            .body(Map.of(
                                                    "status", "ERROR",
                                                    "error", e.getClass().getSimpleName(),
                                                    "message", e.getMessage(),
                                                    "timestamp", LocalDateTime.now().toString()
                                            ))));
                });
    }

    @PostMapping(value = "/batch", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "Recibir eventos en lote", description = "Procesa múltiples eventos en forma de stream reactivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stream de resultados iniciado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    public Flux<Map<String, Object>> receiveBatchEvents(
            @Parameter(description = "Lista de eventos a procesar")
            @RequestBody Flux<EventBatchItem> events) {

        log.info("Recibido lote de eventos para procesamiento");

        return events
                .flatMap(item -> {
                    try {
                        Event event = item.eventType().equals("FRAUD")
                                ? buildFraudEventFromBatch(item)
                                : buildRiskEventFromBatch(item);
                        return eventProcessorService.processEvent(event)
                                .map(result -> Map.<String, Object>of(
                                        "eventId", event.getEventId().toString(),
                                        "status", "PROCESSED",
                                        "processedAt", LocalDateTime.now().toString()
                                ))
                                .onErrorResume(Exception.class, e -> Mono.just(
                                        Map.of(
                                                "eventId", item.eventId(),
                                                "status", "ERROR",
                                                "error", e.getMessage()
                                        )
                                ));
                    } catch (Exception e) {
                        return Flux.just(Map.of(
                                "eventId", item.eventId(),
                                "status", "ERROR",
                                "error", e.getMessage()
                        ));
                    }
                });
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica el estado del servicio")
    @ApiResponse(responseCode = "200", description = "Servicio disponible")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "notification-event-processor",
                "timestamp", LocalDateTime.now().toString()
        ));
    }

    private FraudEventRequest validateFraudRequest(FraudEventRequest request) {
        if (request.customerId() == null || request.customerId() <= 0) {
            throw new EventValidationException("customerId debe ser un número positivo");
        }
        if (request.accountNumber() == null || request.accountNumber().isBlank()) {
            throw new EventValidationException("accountNumber es obligatorio");
        }
        if (request.transactionAmount() == null || request.transactionAmount().signum() <= 0) {
            throw new EventValidationException("transactionAmount debe ser positivo");
        }
        return request;
    }

    private RiskEventRequest validateRiskRequest(RiskEventRequest request) {
        if (request.customerId() == null || request.customerId() <= 0) {
            throw new EventValidationException("customerId debe ser un número positivo");
        }
        if (request.riskType() == null || request.riskType().isBlank()) {
            throw new EventValidationException("riskType es obligatorio");
        }
        if (request.riskScore() < 0 || request.riskScore() > 100) {
            throw new EventValidationException("riskScore debe estar entre 0 y 100");
        }
        return request;
    }

    private FraudEvent buildFraudEvent(FraudEventRequest request) {
        return new FraudEvent(
                UUID.randomUUID(),
                LocalDateTime.now(),
                request.source(),
                request.customerId(),
                request.accountNumber(),
                request.transactionAmount(),
                request.fraudDescription(),
                request.transactionChannel(),
                request.riskLevel()
        );
    }

    private RiskEvent buildRiskEvent(RiskEventRequest request) {
        return new RiskEvent(
                UUID.randomUUID(),
                LocalDateTime.now(),
                request.source(),
                request.customerId(),
                request.riskType(),
                request.riskDescription(),
                request.recommendedAction(),
                request.riskScore()
        );
    }

    private FraudEvent buildFraudEventFromBatch(EventBatchItem item) {
        Map<String, Object> data = item.data();
        return new FraudEvent(
                UUID.fromString(item.eventId()),
                LocalDateTime.now(),
                item.source(),
                ((Number) data.get("customerId")).longValue(),
                (String) data.get("accountNumber"),
                new java.math.BigDecimal(data.get("transactionAmount").toString()),
                (String) data.get("fraudDescription"),
                (String) data.get("transactionChannel"),
                ((Number) data.get("riskLevel")).intValue()
        );
    }

    private RiskEvent buildRiskEventFromBatch(EventBatchItem item) {
        Map<String, Object> data = item.data();
        return new RiskEvent(
                UUID.fromString(item.eventId()),
                LocalDateTime.now(),
                item.source(),
                ((Number) data.get("customerId")).longValue(),
                (String) data.get("riskType"),
                (String) data.get("riskDescription"),
                (String) data.get("recommendedAction"),
                ((Number) data.get("riskScore")).intValue()
        );
    }

    public record FraudEventRequest(
            String source,
            Long customerId,
            String accountNumber,
            java.math.BigDecimal transactionAmount,
            String fraudDescription,
            String transactionChannel,
            int riskLevel
    ) {}

    public record RiskEventRequest(
            String source,
            Long customerId,
            String riskType,
            String riskDescription,
            String recommendedAction,
            int riskScore
    ) {}

    public record EventBatchItem(
            String eventId,
            String eventType,
            String source,
            Map<String, Object> data
    ) {}
}