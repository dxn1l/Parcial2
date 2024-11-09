package com.example.Parcial2.controller;

import com.example.Parcial2.entity.EstacionDeTrabajo;
import com.example.Parcial2.service.EnsamblajeService;
import com.example.Parcial2.service.DistribucionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

@RestController
public class SimulationController {

    private final ExecutorService executorService;
    private final BlockingQueue<Integer> buffer;
    private final DistribucionService distribucionService;

    public SimulationController(ExecutorService executorService, BlockingQueue<Integer> buffer, DistribucionService distribucionService) {
        this.executorService = executorService;
        this.buffer = buffer;
        this.distribucionService = distribucionService;
    }

    @GetMapping("/startSimulation")
    public String startSimulation() {
        // Crear y lanzar hilos de estaciones de trabajo (productores)
        List<EstacionDeTrabajo> estaciones = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EstacionDeTrabajo estacion = new EstacionDeTrabajo((long) i, buffer, i);
            estaciones.add(estacion);
            executorService.submit(estacion);
        }

        // Crear y lanzar el hilo del consumidor (ensamblaje)
        EnsamblajeService ensamblajeService = new EnsamblajeService(buffer);
        executorService.submit(ensamblajeService);

        // Simular la distribución de la caída de bolas
        distribucionService.simularCaidaDeBolas(100);

        return "Simulación iniciada";
    }
}
