package com.example.Parcial2.Service;

import com.example.Parcial2.Entity.Estudiante;
import com.example.Parcial2.Repository.EstudianteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DataProcessorService {

    private final EstudianteRepository estudianteRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public DataProcessorService(EstudianteRepository estudianteRepository, RabbitTemplate rabbitTemplate) {
        this.estudianteRepository = estudianteRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "processQueue")
    public void processData(String message) {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        for (Estudiante estudiante : estudiantes) {
            executorService.submit(estudiante);
        }
        rabbitTemplate.convertAndSend("simulateQueue", "Data Processed");
    }
}