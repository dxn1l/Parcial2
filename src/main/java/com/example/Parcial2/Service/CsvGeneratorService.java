package com.example.Parcial2.Service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

@Service
public class CsvGeneratorService {

    private final RabbitTemplate rabbitTemplate;

    public CsvGeneratorService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void generateCsv(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("id,edad,altura,peso,nota_final,genero\n");
            Random random = new Random();
            for (int i = 0; i < 1000; i++) {
                int id = i;
                int edad = 16 + random.nextInt(3); // Edad entre 16 y 18 años
                int altura = 150 + random.nextInt(51); // Altura entre 150 y 200 cm
                int peso = 50 + random.nextInt(51); // Peso entre 50 y 100 kg
                double notaFinal = Math.round((random.nextDouble() * 10) * 100.0) / 100.0; // Nota final con 2 decimales
                String genero = random.nextBoolean() ? "M" : "F"; // Género M o F

                writer.append(id + "," + edad + "," + altura + "," + peso + "," + notaFinal + "," + genero + "\n");
            }
            rabbitTemplate.convertAndSend("importQueue", filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}