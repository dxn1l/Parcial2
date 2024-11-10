package com.example.Parcial2.controller;

import com.example.Parcial2.Entity.DatoDistribucion;
import com.example.Parcial2.Entity.EstacionDeTrabajo;
import com.example.Parcial2.service.DistribucionDataService;
import com.example.Parcial2.service.EnsamblajeService;
import com.example.Parcial2.service.DistribucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class SimulationController {

    private final DistribucionDataService distribucionDataService;

    private final ExecutorService executorService;
    private final BlockingQueue<Integer> buffer;
    private final DistribucionService distribucionService;



    private int numEstaciones = 10;

    public SimulationController(DistribucionDataService distribucionDataService, ExecutorService executorService, BlockingQueue<Integer> buffer, DistribucionService distribucionService) {
        this.distribucionDataService = distribucionDataService;
        this.executorService = executorService;
        this.buffer = buffer;
        this.distribucionService = distribucionService;


    }

    @GetMapping("/obtenerDatosGradual")
    public SseEmitter obtenerDatosGradualmente(@RequestParam int delay) {
        SseEmitter emitter = new SseEmitter(0L);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                List<DatoDistribucion> datos = distribucionDataService.obtenerDatos();

                for (DatoDistribucion dato : datos) {
                    // Asegúrate de que la cantidad es positiva y la posición es válida (ajusta según tu caso)
                    if (dato.getCantidad() <= 0 || dato.getPosicion() < 0) {
                        System.out.println("Dato con valores no válidos encontrado: " + dato);
                        continue; // Saltar datos no válidos
                    }

                    emitter.send(dato); // Enviar el dato como un objeto JSON
                    Thread.sleep(delay); // Pausa para la formación gradual
                }

                emitter.complete();
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            } finally {
                executor.shutdown();
            }
        });

        return emitter;
    }





    @GetMapping("/startSimulation")
    public String startSimulation(
            @RequestParam(value = "numBolas", required = false, defaultValue = "1000") int numBolas,
            @RequestParam(value = "numEstaciones", required = false, defaultValue = "10") int numEstaciones) {

        System.out.println("=== Iniciando simulación de la fábrica con " + numEstaciones + " estaciones y " + numBolas + " bolas ===");

        // Reiniciar el buffer (vaciarlo)
        buffer.clear();

        // Crear y lanzar hilos de estaciones de trabajo (productores)
        List<EstacionDeTrabajo> estaciones = new ArrayList<>();
        for (int i = 0; i < numEstaciones; i++) {
            EstacionDeTrabajo estacion = new EstacionDeTrabajo((long) i, buffer, i);
            estaciones.add(estacion);
            executorService.submit(estacion); // Ejecuta el hilo de la estación de trabajo
        }

        // Crear y lanzar el hilo del consumidor (ensamblaje)
        EnsamblajeService ensamblajeService = new EnsamblajeService(buffer);
        executorService.submit(ensamblajeService); // Ejecuta el hilo de la línea de ensamblaje

        // Ejecutar la simulación de caída de bolas sin transmisión en tiempo real
        distribucionService.simularCaidaDeBolas(numBolas);

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

    // Endpoint para iniciar la transmisión de la simulación en tiempo real
    @GetMapping("/simulate/stream")
    public SseEmitter streamSimulation(@RequestParam int numBolas) {
        // Desactiva el tiempo de espera del SseEmitter
        SseEmitter emitter = new SseEmitter(0L);

        // Iniciar la simulación en un hilo separado
        new Thread(() -> distribucionService.simularCaidaDeBolasConActualizacion(numBolas, emitter)).start();

        return emitter;
    }


}