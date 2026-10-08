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