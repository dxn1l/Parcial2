package com.example.Parcial2.Service;

import com.example.Parcial2.Entity.Estudiante;
import com.example.Parcial2.Repository.EstudianteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BallDropSimulationService {

    private final EstudianteRepository estudianteRepository;

    public BallDropSimulationService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @RabbitListener(queues = "simulateQueue")
    public void simulateBallDrop(String message) {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        for (Estudiante estudiante : estudiantes) {
            int position = ThreadLocalRandom.current().nextInt(0, 10);
            System.out.println("Ball dropped at position: " + position + " for student with ID: " + estudiante.getId());
            // Add logic to display the distribution
        }
    }
}