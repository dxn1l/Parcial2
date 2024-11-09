package com.example.Parcial2.Service;

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
        // Reiniciar contenedores antes de cada simulación
        contenedores.replaceAll((k, v) -> 0);

        for (int i = 0; i < numBolas; i++) {
            int posicionFinal = simularCaida();
            contenedores.put(posicionFinal, contenedores.get(posicionFinal) + 1);
        }
        mostrarDistribucion();
    }



    private int simularCaida() {
        // Generar un valor con distribución normal centrado en el contenedor central (numClavos / 2)
        double valor = ThreadLocalRandom.current().nextGaussian() * 2 + (numClavos / 2.0);

        // Convertir el valor a una posición entera y limitar entre 0 y numClavos
        int posicion = (int) Math.round(valor);
        return Math.max(0, Math.min(numClavos, posicion));
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
