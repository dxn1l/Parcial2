package com.example.Parcial2.service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Map;
import java.util.HashMap;

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
        int posicion = numClavos / 2;
        for (int i = 0; i < numClavos; i++) {
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
        contenedores.forEach((pos, count) -> System.out.println("Posición " + pos + ": " + count + " bolas"));
    }

    public Map<Integer, Integer> obtenerDistribucion() {
        return new HashMap<>(contenedores); // Para visualizar en frontend si se necesita
    }
}

