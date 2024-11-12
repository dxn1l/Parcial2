package com.example.Parcial2.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class CSVGeneratorService {

    public void generarCSV(String filePath, int numDatos, double media, double desviacionEstandar) throws IOException {
        Random random = new Random();

        try (FileWriter writer = new FileWriter(filePath)) {
            // Escribir encabezado
            writer.append("posicion,cantidad\n");

            // Generar valores siguiendo una distribución normal
            for (int i = 0; i < numDatos; i++) {
                int posicion = (int) Math.round(random.nextGaussian() * desviacionEstandar + media);
                // Limitar la posición al rango de 0 a 9
                posicion = Math.max(0, Math.min(9, posicion));
                writer.append(posicion + "," + 1 + "\n"); // Asigna "cantidad" como 1 para cada posición
            }
        }
    }
}

