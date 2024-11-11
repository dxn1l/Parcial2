package com.example.Parcial2.service;

import com.example.Parcial2.Entity.DatoDistribucion;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class EnsamblajeService {

    private SseEmitter emitter;
    private int processedMessages = 0;

    public void setEmitter(SseEmitter emitter) {
        this.emitter = emitter;

    }

    @RabbitListener(queues = "queue_name")
    public void recibirDatoDesdeRabbitMQ(DatoDistribucion dato) {
        if (emitter != null) {
            try {
                Thread.sleep(50);
                emitter.send(dato);
                processedMessages++;
                System.out.println("RabbitListener Ensamblaje envió dato al frontend: " + dato + " (Total procesados: " + processedMessages + ")");
            } catch (Exception e) {
                emitter.completeWithError(e);
                System.err.println("Error enviando dato al frontend: " + e.getMessage());
            }
        }
    }
}
