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