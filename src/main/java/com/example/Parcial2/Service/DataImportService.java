package com.example.Parcial2.Service;

import com.example.Parcial2.Entity.Estudiante;
import com.example.Parcial2.Repository.EstudianteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class DataImportService {

    private final EstudianteRepository estudianteRepository;
    private final RabbitTemplate rabbitTemplate;

    public DataImportService(EstudianteRepository estudianteRepository, RabbitTemplate rabbitTemplate) {
        this.estudianteRepository = estudianteRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "importQueue")
    public void importCsv(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                Estudiante estudiante = new Estudiante();
                estudiante.setId(Long.parseLong(fields[0]));
                estudiante.setEdad(Double.parseDouble(fields[1]));
                estudiante.setAltura(Double.parseDouble(fields[2]));
                estudiante.setPeso(Double.parseDouble(fields[3]));
                estudiante.setNotaFinal(Double.parseDouble(fields[4]));
                estudiante.setGenero(fields[5]);
                estudianteRepository.save(estudiante);
            }
            rabbitTemplate.convertAndSend("processQueue", "Data Imported");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
