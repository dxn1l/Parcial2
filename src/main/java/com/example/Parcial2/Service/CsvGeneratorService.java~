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
                writer.append(i + "," + (20 + random.nextGaussian() * 5) + "," + (160 + random.nextGaussian() * 10) + "," + (60 + random.nextGaussian() * 15) + "," + (random.nextDouble() * 10) + "," + (random.nextBoolean() ? "M" : "F") + "\n");
            }
            rabbitTemplate.convertAndSend("importQueue", filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}