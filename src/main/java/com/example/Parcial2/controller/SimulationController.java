package com.example.Parcial2.controller;

import com.example.Parcial2.entity.EstacionDeTrabajo;
import com.example.Parcial2.service.EnsamblajeService;
import com.example.Parcial2.service.DistribucionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

@RestController
public class SimulationController {

    private final ExecutorService executorService;
    private final BlockingQueue<Integer> buffer;
    private final DistribucionService distribucionService;

    private int numEstaciones = 10;


    public SimulationController(ExecutorService executorService, BlockingQueue<Integer> buffer, DistribucionService distribucionService) {
        this.executorService = executorService;
        this.buffer = buffer;
        this.distribucionService = distribucionService;
    }

    @GetMapping("/startSimulation")
    public String startSimulation() {
        System.out.println("=== Iniciando simulación de la fábrica con " + numEstaciones + " estaciones ===");

        // Crear y lanzar hilos de estaciones de trabajo (productores)
        List<EstacionDeTrabajo> estaciones = new ArrayList<>();
        for (int i = 0; i < numEstaciones; i++) {
            EstacionDeTrabajo estacion = new EstacionDeTrabajo((long) i, buffer, i);
            estaciones.add(estacion);
            executorService.submit(estacion);
        }

        // Crear y lanzar el hilo del consumidor (ensamblaje)
        EnsamblajeService ensamblajeService = new EnsamblajeService(buffer);
        executorService.submit(ensamblajeService);

        // Simular la distribución de la caída de bolas
        distribucionService.simularCaidaDeBolas(1000);

        System.out.println("=== Simulación en curso... consulte la consola para los detalles de producción y ensamblaje ===");
        return "Simulación iniciada. Consulte la consola para ver los detalles.";
    }


    @PostMapping("/configureSimulation")
    public String configureSimulation(@RequestParam int numEstaciones) {
        // Configura el número de estaciones de trabajo en función de numEstaciones recibido
        this.numEstaciones = numEstaciones;
        System.out.println("Número de estaciones configurado en: " + numEstaciones);
        return "Configuración exitosa: " + numEstaciones + " estaciones.";
    }
    @GetMapping("/simulate")
    public ResponseEntity<Map<Integer, Integer>> simulateAndGetDistribution() {
        // Ejecutar la simulación para calcular la distribución
        distribucionService.simularCaidaDeBolas(10000); // Ajusta el número de bolas según sea necesario

        // Obtener la distribución resultante
        Map<Integer, Integer> distributionData = distribucionService.obtenerDistribucion();

        // Devolver la distribución en un formato JSON
        return ResponseEntity.ok(distributionData);
    }
}


