# Implementación de un servicio de notificación de eventos en un sistema de banca

En un sistema de banca, el servicio de notificación de eventos debe enviar alertas a los clientes cuando se produzcan eventos específicos, como transacciones sospechosas o intentos de fraude. El servicio debe ser capaz de manejar un alto volumen de eventos y garantizar la entrega de las notificaciones en tiempo real. Los eventos provienen de diferentes fuentes, como el motor antifraude, el buró de riesgos y el core bancario. El servicio debe ser idempotente para evitar la duplicación de notificaciones y tolerante a fallos para mantener la disponibilidad en caso de problemas con alguna de las fuentes.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | desarrollo |
| **Nivel** | advanced-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 2 semanas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Definición del modelo de eventos

**Objetivo:** Establecer el modelo de eventos que el servicio debe manejar, incluyendo los atributos y las reglas de validación.

**Tiempo estimado:** 3 días

**Instrucciones:**

- Identificar las fuentes de eventos y los atributos relevantes para cada uno.
- Definir las reglas de validación para cada tipo de evento, incluyendo los umbrales numéricos y los modos de falla.
- Diseñar el modelo de eventos de manera que sea idempotente y tolerante a fallos.

**Entregable:** Modelo de eventos documentado con atributos, reglas de validación y consideraciones de idempotencia y tolerancia a fallos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los diferentes tipos de eventos y cómo se relacionan con las fuentes del dominio.
- Piensa en cómo garantizar que el servicio pueda manejar eventos duplicados sin enviar notificaciones adicionales.

</details>

### Fase 2: Implementación del servicio de notificación

**Objetivo:** Desarrollar el servicio de notificación de eventos que pueda manejar el modelo definido en la fase anterior.

**Tiempo estimado:** 1 semana

**Instrucciones:**

- Implementar el servicio para que reciba eventos de las diferentes fuentes y aplique las reglas de validación definidas.
- Asegurar que el servicio sea idempotente y tolerante a fallos.
- Configurar el servicio para que pueda manejar un alto volumen de eventos en tiempo real.

**Entregable:** Servicio de notificación de eventos funcional que aplica las reglas de validación y es idempotente y tolerante a fallos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo estructurar el servicio para que pueda manejar diferentes tipos de eventos de manera eficiente.
- Piensa en cómo garantizar que el servicio pueda recuperarse de fallos sin perder eventos.

</details>

### Fase 3: Optimización y escalabilidad del servicio

**Objetivo:** Optimizar el servicio de notificación de eventos para mejorar su rendimiento y escalabilidad.

**Tiempo estimado:** 5 días

**Instrucciones:**

- Identificar y optimizar los puntos de rendimiento crítico del servicio.
- Aplicar técnicas de escalabilidad para manejar un mayor volumen de eventos.
- Realizar pruebas de carga para validar la optimización y escalabilidad del servicio.

**Entregable:** Servicio de notificación de eventos optimizado y escalable que puede manejar un mayor volumen de eventos en tiempo real.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo utilizar técnicas de caché y particionamiento para mejorar el rendimiento del servicio.
- Piensa en cómo implementar un sistema de balanceo de carga para distribuir los eventos entre diferentes instancias del servicio.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es un servicio de notificación de eventos y cuáles son sus componentes principales?
- **paraQueSirve**: ¿Para qué sirve el servicio de notificación de eventos en un sistema de banca?
- **comoSeUsa**: ¿Cómo se usa el servicio de notificación de eventos para enviar alertas a los clientes?
- **erroresComunes**: ¿Cuáles son los errores comunes que pueden ocurrir al implementar un servicio de notificación de eventos?
- **queDecisionesImplica**: ¿Qué decisiones implica la implementación de un servicio de notificación de eventos idempotente y tolerante a fallos?

## Criterios de Evaluacion

- Definición clara y completa del modelo de eventos.
- Implementación funcional del servicio de notificación de eventos.
- Servicio idempotente y tolerante a fallos.
- Optimización y escalabilidad del servicio validadas con pruebas de carga.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
