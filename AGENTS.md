# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Implementación de un servicio de notificación de eventos en un sistema de banca**.

| | |
|---|---|
| Tema | desarrollo |
| Nivel | advanced-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean con eventos reactivos |
| Tiempo estimado | 2 semanas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-webflux 3.5.6
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- io.github.resilience4j:resilience4j-reactor 2.2.0
- org.springframework.kafka:spring-kafka n/a
- org.springdoc:springdoc-openapi-starter-webflux-ui 2.9.1
- org.postgresql:postgresql n/a
- org.springframework.boot:spring-boot-starter-test n/a
- io.projectreactor:reactor-test n/a
- org.testcontainers:postgresql 1.20.3

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Definición del modelo de eventos**: Modelo de eventos documentado con atributos, reglas de validación y consideraciones de idempotencia y tolerancia a fallos.
- **Fase 2 — Implementación del servicio de notificación**: Servicio de notificación de eventos funcional que aplica las reglas de validación y es idempotente y tolerante a fallos.
- **Fase 3 — Optimización y escalabilidad del servicio**: Servicio de notificación de eventos optimizado y escalable que puede manejar un mayor volumen de eventos en tiempo real.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/banca/notificacion/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (108)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `com.banca.notificacion.infrastructure.entity.EventEntity`
      El import com.banca.notificacion.infrastructure.entity.EventEntity usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `com.banca.notificacion.infrastructure.repository.EventJpaRepository`
      El import com.banca.notificacion.infrastructure.repository.EventJpaRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/banca/notificacion/NotificationServiceApplication.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Hooks pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/domain/port/EventRepository.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/domain/port/NotificationServicePort.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/config/EventProcessorConfig.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/config/EventProcessorConfig.java` — `reactor.netty.http`
      El import reactor.netty.http.client.HttpClient pertenece a reactor.netty.http, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/config/EventProcessorConfig.java` — `reactor.netty.resources`
      El import reactor.netty.resources.ConnectionProvider pertenece a reactor.netty.resources, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventPublisherAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventPublisherAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/GlobalExceptionHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/GlobalExceptionHandler.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/infrastructure/config/SwaggerConfig.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.models.OpenAPI pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getAccountNumber`
      Se invoca `getAccountNumber` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getTransactionAmount`
      Se invoca `getTransactionAmount` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getRiskLevel`
      Se invoca `getRiskLevel` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getRiskType`
      Se invoca `getRiskType` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getRiskScore`
      Se invoca `getRiskScore` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `EventRepository.existsByEventId`
      Se invoca `existsByEventId` sobre `EventRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.isHighRisk`
      Se invoca `isHighRisk` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.isCritical`
      Se invoca `isCritical` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/application/EventProcessorService.java` — `Event.getRecommendedAction`
      Se invoca `getRecommendedAction` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getClass`
      Se invoca `getClass` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setEventId`
      Se invoca `setEventId` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getEventId`
      Se invoca `getEventId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setEventType`
      Se invoca `setEventType` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getEventType`
      Se invoca `getEventType` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setTimestamp`
      Se invoca `setTimestamp` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getTimestamp`
      Se invoca `getTimestamp` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setSource`
      Se invoca `setSource` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getSource`
      Se invoca `getSource` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setCustomerId`
      Se invoca `setCustomerId` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getCustomerId`
      Se invoca `getCustomerId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setAccountNumber`
      Se invoca `setAccountNumber` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getAccountNumber`
      Se invoca `getAccountNumber` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setTransactionAmount`
      Se invoca `setTransactionAmount` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getTransactionAmount`
      Se invoca `getTransactionAmount` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setFraudDescription`
      Se invoca `setFraudDescription` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getFraudDescription`
      Se invoca `getFraudDescription` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setTransactionChannel`
      Se invoca `setTransactionChannel` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getTransactionChannel`
      Se invoca `getTransactionChannel` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setRiskLevel`
      Se invoca `setRiskLevel` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEvent.getRiskLevel`
      Se invoca `getRiskLevel` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setCreatedAt`
      Se invoca `setCreatedAt` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setRiskType`
      Se invoca `setRiskType` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setRiskDescription`
      Se invoca `setRiskDescription` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setRecommendedAction`
      Se invoca `setRecommendedAction` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java` — `RiskEventEntity.setRiskScore`
      Se invoca `setRiskScore` sobre `RiskEventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getEventId`
      Se invoca `getEventId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getEventType`
      Se invoca `getEventType` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getCustomerId`
      Se invoca `getCustomerId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getTimestamp`
      Se invoca `getTimestamp` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getSource`
      Se invoca `getSource` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getAccountNumber`
      Se invoca `getAccountNumber` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getTransactionAmount`
      Se invoca `getTransactionAmount` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getFraudDescription`
      Se invoca `getFraudDescription` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getTransactionChannel`
      Se invoca `getTransactionChannel` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.getRiskLevel`
      Se invoca `getRiskLevel` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java` — `RiskEvent.isHighRisk`
      Se invoca `isHighRisk` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.source`
      Se invoca `source` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.customerId`
      Se invoca `customerId` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `EventBatchItem.eventType`
      Se invoca `eventType` sobre `EventBatchItem`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `EventBatchItem.eventId`
      Se invoca `eventId` sobre `EventBatchItem`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.accountNumber`
      Se invoca `accountNumber` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.transactionAmount`
      Se invoca `transactionAmount` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.riskType`
      Se invoca `riskType` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.riskScore`
      Se invoca `riskScore` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.fraudDescription`
      Se invoca `fraudDescription` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.transactionChannel`
      Se invoca `transactionChannel` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.riskLevel`
      Se invoca `riskLevel` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.riskDescription`
      Se invoca `riskDescription` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `RiskEventRequest.recommendedAction`
      Se invoca `recommendedAction` sobre `RiskEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `EventBatchItem.data`
      Se invoca `data` sobre `EventBatchItem`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java` — `EventBatchItem.source`
      Se invoca `source` sobre `EventBatchItem`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java` — `EventRepository.existsByEventId`
      Se invoca `existsByEventId` sobre `EventRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java` — `FraudEvent.getEventId`
      Se invoca `getEventId` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java` — `RiskEvent.getEventId`
      Se invoca `getEventId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java` — `FraudEvent.isIdempotentWith`
      Se invoca `isIdempotentWith` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setId`
      Se invoca `setId` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setEventId`
      Se invoca `setEventId` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `FraudEvent.getEventId`
      Se invoca `getEventId` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setEventType`
      Se invoca `setEventType` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `FraudEvent.getEventType`
      Se invoca `getEventType` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setTimestamp`
      Se invoca `setTimestamp` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `FraudEvent.getTimestamp`
      Se invoca `getTimestamp` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setSource`
      Se invoca `setSource` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `FraudEvent.getSource`
      Se invoca `getSource` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setCustomerId`
      Se invoca `setCustomerId` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `FraudEvent.getCustomerId`
      Se invoca `getCustomerId` sobre `FraudEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventEntity.setPayload`
      Se invoca `setPayload` sobre `EventEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `RiskEvent.getEventId`
      Se invoca `getEventId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `RiskEvent.getEventType`
      Se invoca `getEventType` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `RiskEvent.getTimestamp`
      Se invoca `getTimestamp` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `RiskEvent.getSource`
      Se invoca `getSource` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `RiskEvent.getCustomerId`
      Se invoca `getCustomerId` sobre `RiskEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventRepositoryAdapter.findByCustomerId`
      Se invoca `findByCustomerId` sobre `EventRepositoryAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java` — `EventRepositoryAdapter.deleteByEventId`
      Se invoca `deleteByEventId` sobre `EventRepositoryAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (21)

- `pom.xml`
- `src/main/java/com/banca/notificacion/NotificationServiceApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/banca/notificacion/domain/model/Event.java`
- `src/main/java/com/banca/notificacion/domain/model/FraudEvent.java`
- `src/main/java/com/banca/notificacion/domain/model/RiskEvent.java`
- `src/main/java/com/banca/notificacion/domain/model/TransactionEvent.java`
- `src/main/java/com/banca/notificacion/domain/port/EventRepository.java`
- `src/main/java/com/banca/notificacion/domain/port/NotificationServicePort.java`
- `src/main/java/com/banca/notificacion/domain/model/EventValidationException.java`
- `src/main/java/com/banca/notificacion/application/EventProcessorService.java`
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapter.java`
- `src/main/java/com/banca/notificacion/infrastructure/adapter/NotificationServiceAdapter.java`
- `src/main/java/com/banca/notificacion/infrastructure/config/EventProcessorConfig.java`
- `src/main/java/com/banca/notificacion/infrastructure/controller/EventController.java`
- `src/main/java/com/banca/notificacion/infrastructure/config/Resilience4jConfig.java`
- `src/main/java/com/banca/notificacion/infrastructure/adapter/EventPublisherAdapter.java`
- `src/main/java/com/banca/notificacion/infrastructure/controller/GlobalExceptionHandler.java`
- `src/main/java/com/banca/notificacion/infrastructure/config/SwaggerConfig.java`
- `src/test/java/com/banca/notificacion/application/EventProcessorServiceTest.java`
- `src/test/java/com/banca/notificacion/infrastructure/adapter/EventRepositoryAdapterTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/banca/notificacion`
- `src/main/java/com/banca/notificacion/application`
- `src/main/java/com/banca/notificacion/domain`
- `src/main/java/com/banca/notificacion/domain/model`
- `src/main/java/com/banca/notificacion/domain/port`
- `src/main/java/com/banca/notificacion/infrastructure`
- `src/main/java/com/banca/notificacion/infrastructure/adapter`
- `src/main/java/com/banca/notificacion/infrastructure/config`
- `src/main/java/com/banca/notificacion/infrastructure/controller`
- `src/main/resources`
- `src/test/java/com/banca/notificacion`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con eventos reactivos**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced
- Brecha que el reto ataca: Ha trabajado con algún asistente AI en desarrollo (Amazon Q Dev, Kiro, Github Copilot) y lo utiliza en su día a día como desarrollador
- Mision: Candidato con experiencia avanzada en Backend, trabajando en contextos de equipos distribuidos

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
