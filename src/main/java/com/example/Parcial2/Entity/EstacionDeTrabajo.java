package com.example.Parcial2.Entity;

import com.example.Parcial2.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

public class EstacionDeTrabajo {
    private final Long id;
    private final List<DatoDistribucion> datosCSV;
    private final RabbitTemplate rabbitTemplate;

    public EstacionDeTrabajo(Long id, List<DatoDistribucion> datosCSV, RabbitTemplate rabbitTemplate) {
        this.id = id;
        this.datosCSV = datosCSV;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void procesarDatosConRabbitMQ(int delay) {
        Flux.fromIterable(datosCSV)
                .delayElements(Duration.ofMillis(delay))
                .doOnNext(dato -> {
                    rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, dato);
                    System.out.println("Estación " + id + " envió dato a la cola de RabbitMQ: " + dato);
                })
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))) // Reintenta hasta 3 veces con un aumento exponencial
                .doOnError(e -> System.err.println("Error al enviar dato a la cola de RabbitMQ: " + e.getMessage()))
                .doOnComplete(() -> System.out.println("Estación " + id + " completó el envío de todos los datos"))
                .subscribe();
    }
}