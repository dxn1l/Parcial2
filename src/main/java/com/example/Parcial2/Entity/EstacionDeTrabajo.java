package com.example.Parcial2.Entity;

import com.example.Parcial2.config.RabbitMQConfig;
import com.example.Parcial2.Entity.DatoDistribucion;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.core.publisher.Flux;
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
                    rabbitTemplate.convertAndSend("queue_name", dato);
                    System.out.println("Estación " + id + " envió dato a la cola de RabbitMQ: " + dato);
                })
                .doOnComplete(() -> System.out.println("Estación " + id + " completó el envío de todos los datos"))
                .subscribe();
    }

}