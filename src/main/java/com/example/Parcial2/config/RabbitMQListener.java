package com.example.Parcial2.config;

import com.example.Parcial2.Entity.DatoDistribucion;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    public void handleMessage(DatoDistribucion message) {
        // Lógica para manejar el mensaje
        System.out.println("RabbitMQ: Mensaje recibido: " + message);
    }
}