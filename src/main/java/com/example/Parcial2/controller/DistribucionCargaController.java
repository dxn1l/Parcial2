package com.example.Parcial2.controller;

import com.example.Parcial2.Entity.DatoDistribucion;
import com.example.Parcial2.Entity.EstacionDeTrabajo;
import com.example.Parcial2.service.DistribucionDataService;
import com.example.Parcial2.service.EnsamblajeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

@RestController
public class DistribucionCargaController {

    private final DistribucionDataService distribucionDataService;
    private final BlockingQueue<DatoDistribucion> buffer;
    private final ExecutorService executorService;
    private final int numEstaciones = 10; // Número predeterminado de estaciones de trabajo

    public DistribucionCargaController(DistribucionDataService distribucionDataService, BlockingQueue<DatoDistribucion> buffer,
                                       ExecutorService executorService) {
        this.distribucionDataService = distribucionDataService;
        this.buffer = buffer;
        this.executorService = executorService;
    }

    @GetMapping("/cargarDatosCSV")
    public ResponseEntity<Map<String, String>> cargarDatosDesdeCSV(@RequestParam String filePath) throws IOException {
        distribucionDataService.cargarDatosDesdeCSV(filePath);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Datos cargados exitosamente desde el CSV");
        return ResponseEntity.ok(response);
    }




    @GetMapping("/obtenerDatosGradual")
    public SseEmitter obtenerDatosGradual(@RequestParam int delay) {
        SseEmitter emitter = new SseEmitter(0L);  // Desactiva el tiempo de espera
        List<DatoDistribucion> datos = distribucionDataService.obtenerDatos();
        int numDatosPorEstacion = datos.size() / numEstaciones;

        // Dividir los datos en partes para cada estación y procesarlos
        for (int i = 0; i < numEstaciones; i++) {
            int start = i * numDatosPorEstacion;
            int end = (i == numEstaciones - 1) ? datos.size() : start + numDatosPorEstacion;
            List<DatoDistribucion> subListaDatos = datos.subList(start, end);

            EstacionDeTrabajo estacion = new EstacionDeTrabajo((long) i, buffer, subListaDatos);
            executorService.submit(estacion);
        }

        // Crear y lanzar el hilo del ensamblaje para enviar los datos al frontend de forma gradual
        executorService.submit(() -> {
            try {
                EnsamblajeService ensamblaje = new EnsamblajeService(buffer, emitter);
                ensamblaje.procesarGradual(delay);
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }


}
