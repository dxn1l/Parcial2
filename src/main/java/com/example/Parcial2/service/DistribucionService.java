/*package com.example.Parcial2.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DistribucionService {

    private final int numClavos = 10; // Número de niveles en el tablero
    private final Map<Integer, Integer> contenedores = new HashMap<>(); // Acumulación de bolas en contenedores

    public DistribucionService() {
        // Inicializar contenedores (posiciones) para las bolas
        for (int i = 0; i <= numClavos; i++) {
            contenedores.put(i, 0);
        }
    }

    // Método sin transmisión en tiempo real
    public void simularCaidaDeBolas(int numBolas) {
        contenedores.replaceAll((k, v) -> 0); // Reiniciar los contenedores

        for (int i = 0; i < numBolas; i++) {
            int posicionFinal = simularCaida();
            contenedores.put(posicionFinal, contenedores.get(posicionFinal) + 1);
        }
    }

    // Método con transmisión en tiempo real
    public void simularCaidaDeBolasConActualizacion(int numBolas, SseEmitter emitter) {
        contenedores.replaceAll((k, v) -> 0); // Reiniciar los contenedores

        try {
            for (int i = 0; i < numBolas; i++) {
                int posicionFinal = simularCaida();
                contenedores.put(posicionFinal, contenedores.get(posicionFinal) + 1);

                // Crear un mensaje específico para cada bola añadida
                System.out.println("Bola añadida en el contenedor " + posicionFinal + ", estado actual: " + contenedores);
                String mensaje = "Bola añadida en el contenedor " + posicionFinal + ". Estado actual: " + contenedores;

                // Enviar al frontend el estado actual de los contenedores y el mensaje
                if (emitter != null) {
                    try {
                        Map<String, Object> data = new HashMap<>();
                        data.put("contenedores", contenedores);
                        data.put("mensaje", mensaje);

                        emitter.send(SseEmitter.event().data(data, MediaType.APPLICATION_JSON));
                    } catch (IllegalStateException e) {
                        System.out.println("Emisor completado, interrumpiendo la transmisión.");
                        break;
                    } catch (IOException e) {
                        System.out.println("Error al enviar datos: " + e.getMessage());
                        emitter.completeWithError(e);
                        break;
                    }
                }

                // Ajuste de la velocidad de la simulación
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (emitter != null) {
                try {
                    emitter.complete();
                } catch (IllegalStateException e) {
                    System.out.println("El emisor ya fue completado previamente.");
                }
            }
        }

    }

    // Método que simula la caída de una bola y determina su posición final
    private int simularCaida() {
        // Generar un valor que siga una distribución normal centrada en el medio del tablero
        double valorNormal = ThreadLocalRandom.current().nextGaussian() * 2 + (numClavos / 2.0);
        int posicion = (int) Math.round(valorNormal);

        // Asegurarse de que la posición esté dentro de los límites válidos
        return Math.max(0, Math.min(numClavos, posicion));
    }
}*/