package com.example.Parcial2.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Map;
import java.util.HashMap;

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

    public void simularCaidaDeBolas(int numBolas) {
        for (int i = 0; i < numBolas; i++) {
            int posicionFinal = simularCaida(); // Obtener posición final de una bola
            contenedores.put(posicionFinal, contenedores.get(posicionFinal) + 1);
        }
        mostrarDistribucion();
    }

    private int simularCaida() {
        int posicion = numClavos / 2; // Comienza en el centro del tablero
        for (int i = 0; i < numClavos; i++) {
            // Desviar aleatoriamente hacia la izquierda o la derecha
            if (ThreadLocalRandom.current().nextBoolean()) {
                posicion++; // Desviación a la derecha
            } else {
                posicion--; // Desviación a la izquierda
            }
        }
        return Math.max(0, Math.min(numClavos, posicion)); // Limitar la posición dentro del rango
    }


    public void mostrarDistribucion() {
        System.out.println("Distribución de bolas en los contenedores:");

        contenedores.forEach((pos, count) -> {
            String barras = "*".repeat(count); // Crear una barra de asteriscos según la cantidad de bolas
            System.out.printf("Posición %2d: %s (%d)\n", pos, barras, count);
        });
    }

    public Map<Integer, Integer> obtenerDistribucion() {
        return new HashMap<>(contenedores); // Para visualizar en frontend si se necesita
    }
}
