package com.example.Parcial2.service;

import com.example.Parcial2.Entity.DatoDistribucion;
import com.example.Parcial2.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class EnsamblajeService {

    private SseEmitter emitter;
    private int processedMessages = 0;

    public void setEmitter(SseEmitter emitter) {
        this.emitter = emitter;
        this.emitter.onCompletion(() -> this.emitter = null);
        this.emitter.onTimeout(() -> {
            this.emitter = null;
            System.err.println("SseEmitter ha alcanzado el tiempo de espera y ha sido finalizado.");
        });
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void recibirDatoDesdeRabbitMQ(DatoDistribucion dato) {
        if (emitter != null) {
            try {
                emitter.send(dato);
                processedMessages++;
                System.out.println("RabbitListener Ensamblaje envió dato al frontend: " + dato + " (Total procesados: " + processedMessages + ")");
            } catch (Exception e) {
                emitter.completeWithError(e);
                System.err.println("Error enviando dato al frontend: " + e.getMessage());
            }
        } else {
            System.err.println("SseEmitter es null o ha sido completado. No se puede enviar el dato.");
        }
    }
}


