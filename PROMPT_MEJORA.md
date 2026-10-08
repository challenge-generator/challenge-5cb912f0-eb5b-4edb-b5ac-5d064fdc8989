# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/banca/notificacion/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `com.banca.notificacion.infrastructure.entity.EventEntity`: El import com.banca.notificacion.infrastructure.entity.EventEntity usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `com.banca.notificacion.infrastructure.repository.EventJpaRepository`: El import com.banca.notificacion.infrastructure.repository.EventJpaRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/banca/notificacion/NotificationServiceApplication.java` — `reactor.core.publisher`: El import reactor.core.publisher.Hooks pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/domain/port/EventRepository.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/domain/port/NotificationServicePort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/config/EventProcessorConfig.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/config/EventProcessorConfig.java` — `reactor.netty.http`: El import reactor.netty.http.client.HttpClient pertenece a reactor.netty.http, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/config/EventProcessorConfig.java` — `reactor.netty.resources`: El import reactor.netty.resources.ConnectionProvider pertenece a reactor.netty.resources, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `io.swagger.v3`: El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventPublisherAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventPublisherAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/controller/GlobalExceptionHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/controller/GlobalExceptionHandler.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/infrastructure/config/SwaggerConfig.java` — `io.swagger.v3`: El import io.swagger.v3.oas.models.OpenAPI pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getAccountNumber`: Se invoca `getAccountNumber` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getTransactionAmount`: Se invoca `getTransactionAmount` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getRiskLevel`: Se invoca `getRiskLevel` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getRiskType`: Se invoca `getRiskType` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getRiskScore`: Se invoca `getRiskScore` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `EventRepository.existsByEventId`: Se invoca `existsByEventId` sobre `EventRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.isHighRisk`: Se invoca `isHighRisk` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.isCritical`: Se invoca `isCritical` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getRecommendedAction`: Se invoca `getRecommendedAction` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getClass`: Se invoca `getClass` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setEventId`: Se invoca `setEventId` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getEventId`: Se invoca `getEventId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setEventType`: Se invoca `setEventType` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getEventType`: Se invoca `getEventType` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setTimestamp`: Se invoca `setTimestamp` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getTimestamp`: Se invoca `getTimestamp` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setSource`: Se invoca `setSource` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getSource`: Se invoca `getSource` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setCustomerId`: Se invoca `setCustomerId` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getCustomerId`: Se invoca `getCustomerId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setAccountNumber`: Se invoca `setAccountNumber` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getAccountNumber`: Se invoca `getAccountNumber` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setTransactionAmount`: Se invoca `setTransactionAmount` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getTransactionAmount`: Se invoca `getTransactionAmount` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setFraudDescription`: Se invoca `setFraudDescription` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getFraudDescription`: Se invoca `getFraudDescription` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setTransactionChannel`: Se invoca `setTransactionChannel` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getTransactionChannel`: Se invoca `getTransactionChannel` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setRiskLevel`: Se invoca `setRiskLevel` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getRiskLevel`: Se invoca `getRiskLevel` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setCreatedAt`: Se invoca `setCreatedAt` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setRiskType`: Se invoca `setRiskType` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setRiskDescription`: Se invoca `setRiskDescription` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setRecommendedAction`: Se invoca `setRecommendedAction` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setRiskScore`: Se invoca `setRiskScore` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getEventId`: Se invoca `getEventId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getEventType`: Se invoca `getEventType` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getCustomerId`: Se invoca `getCustomerId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getTimestamp`: Se invoca `getTimestamp` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getSource`: Se invoca `getSource` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getAccountNumber`: Se invoca `getAccountNumber` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getTransactionAmount`: Se invoca `getTransactionAmount` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getFraudDescription`: Se invoca `getFraudDescription` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getTransactionChannel`: Se invoca `getTransactionChannel` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getRiskLevel`: Se invoca `getRiskLevel` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.isHighRisk`: Se invoca `isHighRisk` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.source`: Se invoca `source` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.customerId`: Se invoca `customerId` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `EventBatchItem.eventType`: Se invoca `eventType` sobre `EventBatchItem`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `EventBatchItem.eventId`: Se invoca `eventId` sobre `EventBatchItem`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.accountNumber`: Se invoca `accountNumber` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.transactionAmount`: Se invoca `transactionAmount` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.riskType`: Se invoca `riskType` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.riskScore`: Se invoca `riskScore` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.fraudDescription`: Se invoca `fraudDescription` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.transactionChannel`: Se invoca `transactionChannel` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.riskLevel`: Se invoca `riskLevel` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.riskDescription`: Se invoca `riskDescription` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.recommendedAction`: Se invoca `recommendedAction` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `EventBatchItem.data`: Se invoca `data` sobre `EventBatchItem`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `EventBatchItem.source`: Se invoca `source` sobre `EventBatchItem`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java` — `EventRepository.existsByEventId`: Se invoca `existsByEventId` sobre `EventRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java` — `FraudEvent.getEventId`: Se invoca `getEventId` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java` — `RiskEvent.getEventId`: Se invoca `getEventId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java` — `FraudEvent.isIdempotentWith`: Se invoca `isIdempotentWith` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setId`: Se invoca `setId` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setEventId`: Se invoca `setEventId` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `FraudEvent.getEventId`: Se invoca `getEventId` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setEventType`: Se invoca `setEventType` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `FraudEvent.getEventType`: Se invoca `getEventType` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setTimestamp`: Se invoca `setTimestamp` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `FraudEvent.getTimestamp`: Se invoca `getTimestamp` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setSource`: Se invoca `setSource` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `FraudEvent.getSource`: Se invoca `getSource` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setCustomerId`: Se invoca `setCustomerId` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `FraudEvent.getCustomerId`: Se invoca `getCustomerId` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setPayload`: Se invoca `setPayload` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `RiskEvent.getEventId`: Se invoca `getEventId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `RiskEvent.getEventType`: Se invoca `getEventType` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `RiskEvent.getTimestamp`: Se invoca `getTimestamp` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `RiskEvent.getSource`: Se invoca `getSource` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `RiskEvent.getCustomerId`: Se invoca `getCustomerId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventRepositoryAdapter.findByCustomerId`: Se invoca `findByCustomerId` sobre `EventRepositoryAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventRepositoryAdapter.deleteByEventId`: Se invoca `deleteByEventId` sobre `EventRepositoryAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced

### Brecha de conocimiento
Ha trabajado con algún asistente AI en desarrollo (Amazon Q Dev, Kiro, Github Copilot) y lo utiliza en su día a día como desarrollador

### Misión / candidato
Candidato con experiencia avanzada en Backend, trabajando en contextos de equipos distribuidos

### Reto
- Tema: desarrollo
- Seniority: advanced-l2
- Tipo: practical
- Título: Implementación de un servicio de notificación de eventos en un sistema de banca
- Tiempo estimado: 2 semanas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Definición del modelo de eventos — objetivo: Establecer el modelo de eventos que el servicio debe manejar, incluyendo los atributos y las reglas de validación. — entregable (NO resolver): Modelo de eventos documentado con atributos, reglas de validación y consideraciones de idempotencia y tolerancia a fallos.
- Fase 2: Implementación del servicio de notificación — objetivo: Desarrollar el servicio de notificación de eventos que pueda manejar el modelo definido en la fase anterior. — entregable (NO resolver): Servicio de notificación de eventos funcional que aplica las reglas de validación y es idempotente y tolerante a fallos.
- Fase 3: Optimización y escalabilidad del servicio — objetivo: Optimizar el servicio de notificación de eventos para mejorar su rendimiento y escalabilidad. — entregable (NO resolver): Servicio de notificación de eventos optimizado y escalable que puede manejar un mayor volumen de eventos en tiempo real.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.banca</groupId>
    <artifactId>notificacion-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>notificacion-service</name>
    <description>Servicio de notificación de eventos para sistema bancario</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Documentation -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
            <version>2.9.1</version>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <version>1.20.3</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/banca/notificacion/NotificationServiceApplication.java ===
package com.banca.notificacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.core.publisher.Hooks;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableAsync
public class NotificationServiceApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("Servicio de notificaciones iniciado correctamente");
    }

    @Bean
    public reactor.core.scheduler.Scheduler reactorScheduler() {
        return reactor.core.scheduler.Schedulers.newBoundedElastic(
            Runtime.getRuntime().availableProcessors() * 2,
            Integer.MAX_VALUE,
            "reactor-elastic"
        );
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: notificacion-service
  profiles:
    active: dev
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/notificaciones
    username: postgres
    password: postgres
    pool:
      enabled: true
      initial-size: 5
      max-size: 20
      max-idle-time: 30m
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: notificacion-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "com.banca.notificacion.domain.model"
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      properties:
        spring.json.add.type.headers: false

server:
  port: 8080
  shutdown: graceful

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      probes:
        enabled: true
      group:
        readiness:
          include: readinessState,db
        liveness:
          include: livenessState
  health:
    livenessstate:
      enabled: true
    readinessstate:
      enabled: true

resilience4j:
  circuitbreaker:
    configs:
      default:
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 5s
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        recordExceptions:
          - org.springframework.dao.DataAccessException
          - java.util.concurrent.TimeoutException
          - java.io.IOException
    instances:
      eventProcessor:
        baseConfig: default
  retry:
    configs:
      default:
        maxAttempts: 3
        waitDuration: 100ms
        retryExceptions:
          - org.springframework.dao.DataAccessException
          - java.util.concurrent.TimeoutException
    instances:
      eventProcessor:
        baseConfig: default

logging:
  level:
    root: INFO
    com.banca.notificacion: DEBUG
    org.springframework.web: INFO
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE

// === ARCHIVO: src/main/java/com/banca/notificacion/domain/model/Event.java ===
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

// === ARCHIVO: src/main/java/com/banca/notificacion/domain/model/FraudEvent.java ===
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

// === ARCHIVO: src/main/java/com/banca/notificacion/domain/model/RiskEvent.java ===
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

// === ARCHIVO: src/main/java/com/banca/notificacion/domain/model/TransactionEvent.java ===
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

// === ARCHIVO: src/main/java/com/banca/notificacion/domain/port/EventRepository.java ===
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

// === ARCHIVO: src/main/java/com/banca/notificacion/domain/port/NotificationServicePort.java ===
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

// === ARCHIVO: src/main/java/com/banca/notificacion/domain/model/EventValidationException.java ===
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

// === ARCHIVO: src/main/java/com/banca/notificacion/application/EventProcessorService.java ===
package com.banca.notificacion.application;

import com.banca.notificacion.domain.model.Event;
import com.banca.notificacion.domain.model.EventValidationException;
import com.banca.notificacion.domain.model.FraudEvent;
import com.banca.notificacion.domain.model.RiskEvent;
import com.banca.notificacion.domain.port.EventRepository;
import com.banca.notificacion.domain.port.NotificationServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventProcessorService {
    private static final Logger log = LoggerFactory.getLogger(EventProcessorService.class);

    private final EventRepository eventRepository;
    private final NotificationServicePort notificationServicePort;

    public EventProcessorService(EventRepository eventRepository, 
                                  NotificationServicePort notificationServicePort) {
        this.eventRepository = eventRepository;
        this.notificationServicePort = notificationServicePort;
    }

    public Mono<Event> processEvent(Event event) {
        log.info("Procesando evento {} con ID {} de fuente {} en timestamp {}",
                event.getEventType(), event.getEventId(), event.getSource(), event.getTimestamp());

        return validateEvent(event)
            .flatMap(this::checkIdempotency)
            .flatMap(this::applyBusinessRules)
            .flatMap(this::persistAndNotify)
            .doOnSuccess(savedEvent -> log.info("Evento {} procesado exitosamente", savedEvent.getEventId()))
            .doOnError(error -> log.error("Error al procesar evento {}: {}", 
                    event.getEventId(), error.getMessage()));
    }

    public Flux<Event> processEventsBatch(Flux<Event> events) {
        return events
            .flatMap(this::processEvent)
            .doOnComplete(() -> log.info("Lote de eventos procesado completamente"))
            .doOnError(error -> log.error("Error en procesamiento por lotes: {}", error.getMessage()));
    }

    private Mono<Event> validateEvent(Event event) {
        if (event.getEventId() == null) {
            return Mono.error(EventValidationException.missingRequiredField(
                null, event.getEventType(), "eventId"));
        }
        if (event.getEventType() == null || event.getEventType().isBlank()) {
            return Mono.error(EventValidationException.missingRequiredField(
                event.getEventId(), event.getEventType(), "eventType"));
        }
        if (event.getCustomerId() == null) {
            return Mono.error(EventValidationException.missingRequiredField(
                event.getEventId(), event.getEventType(), "customerId"));
        }

        if (event instanceof FraudEvent fraudEvent) {
            return validateFraudEvent(fraudEvent);
        } else if (event instanceof RiskEvent riskEvent) {
            return validateRiskEvent(riskEvent);
        }

        return Mono.just(event);
    }

    private Mono<Event> validateFraudEvent(FraudEvent event) {
        if (event.getAccountNumber() == null || event.getAccountNumber().isBlank()) {
            return Mono.error(EventValidationException.missingRequiredField(
                event.getEventId(), event.getEventType(), "accountNumber"));
        }
        if (event.getTransactionAmount() == null || event.getTransactionAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return Mono.error(EventValidationException.invalidFieldValue(
                event.getEventId(), event.getEventType(), "transactionAmount", event.getTransactionAmount()));
        }
        if (event.getRiskLevel() < 0 || event.getRiskLevel() > 100) {
            return Mono.error(EventValidationException.invalidFieldValue(
                event.getEventId(), event.getEventType(), "riskLevel", event.getRiskLevel()));
        }
        return Mono.just(event);
    }

    private Mono<Event> validateRiskEvent(RiskEvent event) {
        if (event.getRiskType() == null || event.getRiskType().isBlank()) {
            return Mono.error(EventValidationException.missingRequiredField(
                event.getEventId(), event.getEventType(), "riskType"));
        }
        if (event.getRiskScore() < 0 || event.getRiskScore() > 100) {
            return Mono.error(EventValidationException.invalidFieldValue(
                event.getEventId(), event.getEventType(), "riskScore", event.getRiskScore()));
        }
        return Mono.just(event);
    }

    private Mono<Event> checkIdempotency(Event event) {
        return eventRepository.existsByEventId(event.getEventId())
            .flatMap(exists -> {
                if (exists) {
                    log.warn("Evento duplicado detectado: {}", event.getEventId());
                    return Mono.empty();
                }
                return Mono.just(event);
            });
    }

    private Mono<Event> applyBusinessRules(Event event) {
        if (event instanceof FraudEvent fraudEvent) {
            return applyFraudBusinessRules(fraudEvent);
        } else if (event instanceof RiskEvent riskEvent) {
            return applyRiskBusinessRules(riskEvent);
        }
        return Mono.just(event);
    }

    private Mono<Event> applyFraudBusinessRules(FraudEvent event) {
        if (event.isHighRisk()) {
            log.info("Evento de fraude de alto riesgo detectado para cuenta {} - requiere atención inmediata",
                    event.getAccountNumber());
        }
        return Mono.just(event);
    }

    private Mono<Event> applyRiskBusinessRules(RiskEvent event) {
        if (event.isCritical()) {
            log.info("Evento de riesgo crítico detectado para cliente {} - requiere acción inmediata: {}",
                    event.getCustomerId(), event.getRecommendedAction());
        }
        return Mono.just(event);
    }

    private Mono<Event> persistAndNotify(Event event) {
        return eventRepository.save(event)
            .flatMap(savedEvent -> {
                String notificationMessage = buildNotificationMessage(savedEvent);
                return notificationServicePort.sendNotification(
                        savedEvent.getCustomerId(),
                        notificationMessage,
                        savedEvent.getEventType()
                    )
                    .thenReturn(savedEvent);
            });
    }

    private String buildNotificationMessage(Event event) {
        String baseMessage = "Notificación de evento bancario - Tipo: " + event.getEventType();
        
        if (event instanceof FraudEvent fraudEvent) {
            return baseMessage + " - Cuenta: " + fraudEvent.getAccountNumber() + 
                   " - Monto: $" + fraudEvent.getTransactionAmount() + 
                   " - Nivel de riesgo: " + fraudEvent.getRiskLevel() + 
                   " - Descripción: " + fraudEvent.getFraudDescription();
        } else if (event instanceof RiskEvent riskEvent) {
            return baseMessage + " - Tipo de riesgo: " + riskEvent.getRiskType() + 
                   " - Puntuación: " + riskEvent.getRiskScore() + 
                   " - Acción recomendada: " + riskEvent.getRecommendedAction();
        }
        
        return baseMessage + " - ID del evento: " + event.getEventId();
    }
}

// === ARCHIVO: src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java ===
package com.banca.notificacion.infrastructure.adapter;

import com.banca.notificacion.domain.model.Event;
import com.banca.notificacion.domain.model.FraudEvent;
import com.banca.notificacion.domain.model.RiskEvent;
import com.banca.notificacion.domain.port.EventRepository;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class EventRepositoryAdapter implements EventRepository {
    private static final Logger log = LoggerFactory.getLogger(EventRepositoryAdapter.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Mono<Event> save(Event event) {
        return Mono.fromCallable(() -> {
            if (event instanceof FraudEvent fraudEvent) {
                return persistFraudEvent(fraudEvent);
            } else if (event instanceof RiskEvent riskEvent) {
                return persistRiskEvent(riskEvent);
            }
            throw new IllegalArgumentException("Tipo de evento no soportado: " + event.getClass().getName());
        });
    }

    private Event persistFraudEvent(FraudEvent event) {
        FraudEventEntity entity = new FraudEventEntity();
        entity.setEventId(event.getEventId());
        entity.setEventType(event.getEventType());
        entity.setTimestamp(event.getTimestamp());
        entity.setSource(event.getSource());
        entity.setCustomerId(event.getCustomerId());
        entity.setAccountNumber(event.getAccountNumber());
        entity.setTransactionAmount(event.getTransactionAmount());
        entity.setFraudDescription(event.getFraudDescription());
        entity.setTransactionChannel(event.getTransactionChannel());
        entity.setRiskLevel(event.getRiskLevel());
        entity.setCreatedAt(LocalDateTime.now());

        entityManager.persist(entity);
        log.debug("Evento de fraude persistido: {}", event.getEventId());
        return event;
    }

    private Event persistRiskEvent(RiskEvent event) {
        RiskEventEntity entity = new RiskEventEntity();
        entity.setEventId(event.getEventId());
        entity.setEventType(event.getEventType());
        entity.setTimestamp(event.getTimestamp());
        entity.setSource(event.getSource());
        entity.setCustomerId(event.getCustomerId());
        entity.setRiskType(event.getRiskType());
        entity.setRiskDescription(event.getRiskDescription());
        entity.setRecommendedAction(event.getRecommendedAction());
        entity.setRiskScore(event.getRiskScore());
        entity.setCreatedAt(LocalDateTime.now());

        entityManager.persist(entity);
        log.debug("Evento de riesgo persistido: {}", event.getEventId());
        return event;
    }

    @Override
    public Mono<Boolean> existsByEventId(UUID eventId) {
        return Mono.fromCallable(() -> {
            Long count = entityManager.createQuery(
                "SELECT COUNT(e) FROM EventEntity e WHERE e.eventId = :eventId", Long.class)
                .setParameter("eventId", eventId)
                .getSingleResult();
            return count != null && count > 0;
        });
    }

    @Override
    public Mono<Event> findByEventId(UUID eventId) {
        return Mono.fromCallable(() -> {
            var query = entityManager.createQuery(
                "SELECT e FROM EventEntity e WHERE e.eventId = :eventId", EventEntity.class)
                .setParameter("eventId", eventId);
            
            var entities = query.getResultList();
            if (entities.isEmpty()) {
                return null;
            }
            return entities.get(0).toDomainEvent();
        });
    }

    @Entity
    @Table(name = "events")
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn(name = "event_type", discriminatorType = DiscriminatorType.STRING)
    public static abstract class EventEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private UUID eventId;

        @Column(nullable = false)
        private String eventType;

        @Column(nullable = false)
        private LocalDateTime timestamp;

        @Column(nullable = false)
        private String source;

        @Column(nullable = false)
        private Long customerId;

        @Column(nullable = false)
        private LocalDateTime createdAt;

        public abstract Event toDomainEvent();

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public UUID getEventId() { return eventId; }
        public void setEventId(UUID eventId) { this.eventId = eventId; }
        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }

    @Entity
    @DiscriminatorValue("FRAUD")
    public static class FraudEventEntity extends EventEntity {
        @Column(nullable = false)
        private String accountNumber;

        @Column(nullable = false, precision = 19, scale = 4)
        private BigDecimal transactionAmount;

        @Column(length = 1000)
        private String fraudDescription;

        @Column
        private String transactionChannel;

        @Column(nullable = false)
        private int riskLevel;

        @Override
        public Event toDomainEvent() {
            return new FraudEvent(
                getEventId(),
                getTimestamp(),
                getSource(),
                getCustomerId(),
                accountNumber,
                transactionAmount,
                fraudDescription,
                transactionChannel,
                riskLevel
            );
        }

        public String getAccountNumber() { return accountNumber; }
        public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
        public BigDecimal getTransactionAmount() { return transactionAmount; }
        public void setTransactionAmount(BigDecimal transactionAmount) { this.transactionAmount = transactionAmount; }
        public String getFraudDescription() { return fraudDescription; }
        public void setFraudDescription(String fraudDescription) { this.fraudDescription = fraudDescription; }
        public String getTransactionChannel() { return transactionChannel; }
        public void setTransactionChannel(String transactionChannel) { this.transactionChannel = transactionChannel; }
        public int getRiskLevel() { return riskLevel; }
        public void setRiskLevel(int riskLevel) { this.riskLevel = riskLevel; }
    }

    @Entity
    @DiscriminatorValue("RISK")
    public static class RiskEventEntity extends EventEntity {
        @Column(nullable = false)
        private String riskType;

        @Column(length = 1000)
        private String riskDescription;

        @Column(length = 500)
        private String recommendedAction;

        @Column(nullable = false)
        private int riskScore;

        @Override
        public Event toDomainEvent() {
            return new RiskEvent(
                getEventId(),
                getTimestamp(),
                getSource(),
                getCustomerId(),
                riskType,
                riskDescription,
                recommendedAction,
                riskScore
            );
        }

        public String getRiskType() { return riskType; }
        public void setRiskType(String riskType) { this.riskType = riskType; }
        public String getRiskDescription() { return riskDescription; }
        public void setRiskDescription(String riskDescription) { this.riskDescription = riskDescription; }
        public String getRecommendedAction() { return recommendedAction; }
        public void setRecommendedAction(String recommendedAction) { this.recommendedAction = recommendedAction; }
        public int getRiskScore() { return riskScore; }
        public void setRiskScore(int riskScore) { this.riskScore = riskScore; }
    }
}

// === ARCHIVO: src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java ===
package com.banca.notificacion.infrastructure.adapter;

import com.banca.notificacion.domain.model.Event;
import com.banca.notificacion.domain.model.FraudEvent;
import com.banca.notificacion.domain.model.RiskEvent;
import com.banca.notificacion.domain.port.NotificationServicePort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Component
public class NotificationServiceAdapter implements NotificationServicePort {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceAdapter.class);
    private static final String NOTIFICATION_SERVICE_NAME = "notificationService";

    private final WebClient webClient;
    private final String notificationEndpoint;
    private final Duration timeout;

    public NotificationServiceAdapter(
            WebClient.Builder webClientBuilder,
            @Value("${app.notification.service.url:http://notification-internal:8080/api/notifications}") String notificationEndpoint,
            @Value("${app.notification.service.timeout:5000}") int timeoutMs) {
        this.webClient = webClientBuilder.baseUrl(notificationEndpoint).build();
        this.notificationEndpoint = notificationEndpoint;
        this.timeout = Duration.ofMillis(timeoutMs);
    }

    @Override
    @CircuitBreaker(name = NOTIFICATION_SERVICE_NAME, fallbackMethod = "sendNotificationFallback")
    public Mono<Boolean> sendNotification(Event event) {
        log.info("Enviando notificación para evento {} de tipo {} del cliente {}",
                event.getEventId(), event.getEventType(), event.getCustomerId());

        Map<String, Object> payload = buildNotificationPayload(event);

        return webClient.post()
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(timeout)
                .map(response -> {
                    log.info("Notificación enviada exitosamente para evento {}", event.getEventId());
                    return Boolean.TRUE;
                })
                .doOnError(error -> log.error("Error enviando notificación para evento {}: {}",
                        event.getEventId(), error.getMessage()));
    }

    private Mono<Boolean> sendNotificationFallback(Event event, Throwable throwable) {
        log.warn("Fallback activado para evento {} debido a: {}. Registrando para retry posterior.",
                event.getEventId(), throwable.getMessage());
        return Mono.just(Boolean.FALSE);
    }

    private Map<String, Object> buildNotificationPayload(Event event) {
        Map<String, Object> payload = Map.of(
                "eventId", event.getEventId().toString(),
                "eventType", event.getEventType(),
                "timestamp", event.getTimestamp().toString(),
                "source", event.getSource(),
                "customerId", event.getCustomerId()
        );

        if (event instanceof FraudEvent fraudEvent) {
            return enrichWithFraudData(payload, fraudEvent);
        } else if (event instanceof RiskEvent riskEvent) {
            return enrichWithRiskData(payload, riskEvent);
        }

        return payload;
    }

    private Map<String, Object> enrichWithFraudData(Map<String, Object> payload, FraudEvent event) {
        return Map.of(
                "eventId", event.getEventId().toString(),
                "eventType", event.getEventType(),
                "timestamp", event.getTimestamp().toString(),
                "source", event.getSource(),
                "customerId", event.getCustomerId(),
                "accountNumber", event.getAccountNumber(),
                "transactionAmount", event.getTransactionAmount().toString(),
                "fraudDescription", event.getFraudDescription(),
                "transactionChannel", event.getTransactionChannel(),
                "riskLevel", event.getRiskLevel(),
                "priority", event.isHighRisk() ? "HIGH" : "MEDIUM",
                "message", buildFraudMessage(event)
        );
    }

    private Map<String, Object> enrichWithRiskData(Map<String, Object> payload, RiskEvent event) {
        return Map.of(
                "eventId", event.getEventId().toString(),
                "eventType", event.getEventType(),
                "timestamp", event.getTimestamp().toString(),
                "source", event.getSource(),
                "customerId", event.getCustomerId(),
                "riskType", event.getRiskType(),
                "riskDescription", event.getRiskDescription(),
                "recommendedAction", event.getRecommendedAction(),
                "riskScore", event.getRiskScore(),
                "priority", event.isCritical() ? "HIGH" : "MEDIUM",
                "message", buildRiskMessage(event)
        );
    }

    private String buildFraudMessage(FraudEvent event) {
        if (event.isHighRisk()) {
            return "ALERTA CRÍTICA: Se ha detectado una transacción sospechosa de alto riesgo en tu cuenta. " +
                    "Monto: $" + event.getTransactionAmount() + ". Por favor contacta inmediatamente.";
        }
        return "Notificación de seguridad: Se ha detectado una actividad inusual en tu cuenta. " +
                "Monto: $" + event.getTransactionAmount() + ".";
    }

    private String buildRiskMessage(RiskEvent event) {
        if (event.isCritical()) {
            return "ALERTA DE RIESGO CRÍTICO: " + event.getRiskDescription() + ". " +
                    "Acción recomendada: " + event.getRecommendedAction();
        }
        return "Notificación de riesgo: " + event.getRiskDescription() + ". " +
                "Puntuación de riesgo: " + event.getRiskScore();
    }

    @Override
    public Mono<Boolean> sendBulkNotifications(java.util.List<Event> events) {
        log.info("Enviando {} notificaciones en bulk", events.size());
        return Mono.whenDelayError(events.stream()
                        .map(this::sendNotification)
                        .toList())
                .then(Mono.just(Boolean.TRUE))
                .onErrorResume(error -> {
                    log.error("Error en envío masivo: {}", error.getMessage());
                    return Mono.just(Boolean.FALSE);
                });
    }
}

// === ARCHIVO: src/main/java/com/banca/notificacion/infrastructure/config/EventProcessorConfig.java ===
package com.banca.notificacion.infrastructure.config;

import com.banca.notificacion.application.EventProcessorService;
import com.banca.notificacion.domain.port.EventRepository;
import com.banca.notificacion.domain.port.NotificationServicePort;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Configuration
public class EventProcessorConfig {

    private static final Logger log = LoggerFactory.getLogger(EventProcessorConfig.class);

    @Value("${app.webclient.connection-timeout:5000}")
    private int connectionTimeout;

    @Value("${app.webclient.response-timeout:10000}")
    private int responseTimeout;

    @Value("${app.webclient.max-connections:100}")
    private int maxConnections;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        ConnectionProvider provider = ConnectionProvider.builder("eventProcessorPool")
                .maxConnections(maxConnections)
                .pendingAcquireTimeout(Duration.ofMillis(responseTimeout))
                .maxIdleTime(Duration.ofSeconds(30))
                .build();

        HttpClient httpClient = HttpClient.create(provider)
                .responseTimeout(Duration.ofMillis(responseTimeout))
                .option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout);

        return builder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(
            @Value("${resilience4j.circuitbreaker.config.default.sliding-window-size:10}") int slidingWindowSize,
            @Value("${resilience4j.circuitbreaker.config.default.permitted-number-of-calls-in-half-open-state:3}") int permittedCalls,
            @Value("${resilience4j.circuitbreaker.config.default.wait-duration-in-open-state:30s}") Duration waitDuration,
            @Value("${resilience4j.circuitbreaker.config.default.failure-rate-threshold:50}") float failureRateThreshold) {

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .slidingWindowSize(slidingWindowSize)
                .permittedNumberOfCallsInHalfOpenState(permittedCalls)
                .waitDurationInOpenState(waitDuration)
                .failureRateThreshold(failureRateThreshold)
                .slowCallRateThreshold(80.0f)
                .slowCallDurationThreshold(Duration.ofSeconds(2))
                .build();

        log.info("CircuitBreaker configurado con slidingWindow={}, permittedCalls={}, waitDuration={}s, failureRateThreshold={}%",
                slidingWindowSize, permittedCalls, waitDuration.getSeconds(), failureRateThreshold);

        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry(
            @Value("${resilience4j.retry.config.default.max-attempts:3}") int maxAttempts,
            @Value("${resilience4j.retry.config.default.wait-duration:1000ms}") Duration waitDuration,
            @Value("${resilience4j.retry.config.default.retry-exceptions:java.io.IOException,java.net.SocketTimeoutException}") String retryExceptions) {

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(maxAttempts)
                .waitDuration(waitDuration)
                .retryExceptions(IOException.class, java.net.SocketTimeoutException.class)
                .build();

        log.info("Retry configurado con maxAttempts={}, waitDuration={}ms",
                maxAttempts, waitDuration.toMillis());

        return RetryRegistry.of(config);
    }

    @Bean
    public EventProcessorService eventProcessorService(
            EventRepository eventRepository,
            NotificationServicePort notificationServicePort,
            CircuitBreakerRegistry circuitBreakerRegistry,
            RetryRegistry retryRegistry) {

        log.info("Inicializando EventProcessorService con CircuitBreaker y Retry configurados");
        return new EventProcessorService(eventRepository, notificationServicePort,
                circuitBreakerRegistry, retryRegistry);
    }
}

// === ARCHIVO: src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java ===
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

// === ARCHIVO: src/main/java/com/banca/notificacion/infrastructure/config/Resilience4jConfig.java ===
package com.banca.notificacion.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .ignoreExceptions(Exception.class)
                .build();
        return RetryRegistry.of(config);
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(100)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofMillis(500))
                .build();
        return RateLimiterRegistry.of(config);
    }
}

// === ARCHIVO: src/main/java/com/banca/notificacion/infrastructure/adapter/EventPublisherAdapter.java ===
package com.banca.notificacion.infrastructure.adapter;

import com.banca.notificacion.domain.model.Event;
import com.banca.notificacion.domain.port.EventRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
public class EventPublisherAdapter {

    private static final Logger log = LoggerFactory.getLogger(EventPublisherAdapter.class);
    private static final String EVENT_TOPIC = "banking-events";

    private final KafkaTemplate<String, Event> kafkaTemplate;
    private final EventRepository eventRepository;
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;

    public EventPublisherAdapter(
            KafkaTemplate<String, Event> kafkaTemplate,
            EventRepository eventRepository,
            CircuitBreakerRegistry circuitBreakerRegistry,
            RetryRegistry retryRegistry) {
        this.kafkaTemplate = kafkaTemplate;
        this.eventRepository = eventRepository;
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("eventPublisher");
        this.retry = retryRegistry.retry("eventPublisher");
    }

    public Mono<Boolean> publishEvent(Event event) {
        return Mono.fromCallable(() -> {
            log.info("Publishing event {} of type {} to Kafka topic", 
                    event.getEventId(), event.getEventType());
            
            CompletableFuture<SendResult<String, Event>> future = kafkaTemplate.send(
                    EVENT_TOPIC,
                    event.getEventId().toString(),
                    event
            );
            
            SendResult<String, Event> result = future.get();
            log.info("Event {} successfully published to partition {} at offset {}",
                    event.getEventId(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
            
            return true;
        })
        .transformDeferred(CircuitBreaker.operatorOf(circuitBreaker))
        .transformDeferred(Retry.operatorOf(retry))
        .doOnSuccess(success -> {
            if (Boolean.TRUE.equals(success)) {
                log.debug("Event {} published and acknowledged", event.getEventId());
            }
        })
        .doOnError(error -> {
            log.error("Failed to publish event {} after retries: {}", 
                    event.getEventId(), error.getMessage(), error);
        })
        .onErrorResume(error -> {
            log.warn("Circuit breaker open or all retries failed for event {}, " +
                    "storing for later processing", event.getEventId());
            return persistEventForRetry(event)
                    .thenReturn(false);
        });
    }

    private Mono<Void> persistEventForRetry(Event event) {
        return Mono.fromRunnable(() -> {
            try {
                eventRepository.save(event);
                log.info("Event {} persisted for later processing due to publish failure",
                        event.getEventId());
            } catch (Exception e) {
                log.error("Failed to persist event {} for later processing: {}",
                        event.getEventId(), e.getMessage(), e);
                throw new RuntimeException("Failed to queue event for retry", e);
            }
        });
    }

    public Mono<Boolean> publishEventWithFallback(Event event) {
        return publishEvent(event)
                .onErrorResume(e -> {
                    log.error("All publishing attempts failed for event {}, " +
                            "marking as failed permanently", event.getEventId());
                    return Mono.just(false);
                });
    }
}

// === ARCHIVO: src/main/java/com/banca/notificacion/infrastructure/controller/GlobalExceptionHandler.java ===
package com.banca.notificacion.infrastructure.controller;

import com.banca.notificacion.domain.model.EventValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EventValidationException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleEventValidationException(
            EventValidationException ex) {
        log.warn("Validation error: {}", ex.getMessage());
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Error");
        body.put("message", ex.getMessage());
        body.put("eventType", ex.getEventType());
        if (ex.getEventId() != null) {
            body.put("eventId", ex.getEventId().toString());
        }
        
        return Mono.just(ResponseEntity.badRequest().body(body));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleWebExchangeBindException(
            WebExchangeBindException ex) {
        log.warn("Request binding error: {}", ex.getMessage());
        
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Invalid Request");
        body.put("message", errors);
        
        return Mono.just(ResponseEntity.badRequest().body(body));
    }

    @ExceptionHandler(WebClientResponseException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleWebClientResponseException(
            WebClientResponseException ex) {
        log.error("External service error: {} - {}", ex.getStatusCode(), ex.getMessage());
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", ex.getStatusCode().value());
        body.put("error", "External Service Error");
        body.put("message", "Failed to communicate with external service");
        
        HttpStatus status = ex.getStatusCode();
        if (status == HttpStatus.SERVICE_UNAVAILABLE) {
            body.put("message", "External service is temporarily unavailable");
            return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body));
        } else if (status == HttpStatus.GATEWAY_TIMEOUT) {
            body.put("message", "External service request timed out");
            return Mono.just(ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(body));
        }
        
        return Mono.just(ResponseEntity.status(status).body(body));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        
        return Mono.just(ResponseEntity.badRequest().body(body));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleGenericException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", "An unexpected error occurred. Please try again later.");
        
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
    }
}

// === ARCHIVO: src/main/java/com/banca/notificacion/infrastructure/config/SwaggerConfig.java ===
package com.banca.notificacion.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("!prod")
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Servicio de Notificación de Eventos")
                        .version("1.0.0")
                        .description("""
                                Servicio de notificación de eventos para el sistema bancario.
                                Maneja eventos de fraude, riesgo y transacciones en tiempo real.
                                """)
                        .contact(new Contact()
                                .name("Equipo de Desarrollo Banca")
                                .email("desarrollo@banca.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Servidor de desarrollo local"),
                        new Server()
                                .url("https://api.banca.com/notificacion")
                                .description("Servidor de producción")
                ));
    }
}

// === ARCHIVO: src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java ===
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

// === ARCHIVO: src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java ===
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
```
