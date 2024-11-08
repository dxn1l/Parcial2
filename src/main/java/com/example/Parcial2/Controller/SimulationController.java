package com.example.Parcial2.Controller;

import com.example.Parcial2.Entity.Estudiante;
import com.example.Parcial2.Repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class SimulationController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @GetMapping("/simulate")
    public Flux<String> simulateBallDrop() {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        return Flux.fromIterable(estudiantes)
                .map(estudiante -> {
                    int position = ThreadLocalRandom.current().nextInt(0, 10);
                    return "Ball dropped at position: " + position + " for student with ID: " + estudiante.getId();
                });
    }
}