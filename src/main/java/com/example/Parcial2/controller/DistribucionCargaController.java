package com.example.Parcial2.controller;

import com.example.Parcial2.Entity.DatoDistribucion;
import com.example.Parcial2.Entity.EstacionDeTrabajo;
import com.example.Parcial2.service.DistribucionDataService;
import com.example.Parcial2.service.EnsamblajeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

@RestController
public class DistribucionCargaController {

    private final DistribucionDataService distribucionDataService;
    private final BlockingQueue<DatoDistribucion> buffer;
    private final ExecutorService executorService;

    public DistribucionCargaController(DistribucionDataService distribucionDataService, BlockingQueue<DatoDistribucion> buffer,
                                       ExecutorService executorService) {
        this.distribucionDataService = distribucionDataService;
        this.buffer = buffer;
        this.executorService = executorService;
    }

    @GetMapping("/cargarDatosCSV")
    public SseEmitter cargarDatosDesdeCSV(@RequestParam String filePath) throws IOException {
        SseEmitter emitter = new SseEmitter(0L);
        List<DatoDistribucion> datosCSV = distribucionDataService.cargarDatosDesdeCSV(filePath);

        for (int i = 0; i < 10; i++) { // Ajusta el número de estaciones según tus necesidades
            EstacionDeTrabajo estacion = new EstacionDeTrabajo((long) i, buffer, datosCSV);
            executorService.submit(estacion);
        }

        EnsamblajeService ensamblajeService = new EnsamblajeService(buffer, emitter);
        executorService.submit(ensamblajeService);

        return emitter;
    }

    @GetMapping("/obtenerDatosGradual")
    public SseEmitter obtenerDatosGradual(@RequestParam int delay) {
        SseEmitter emitter = new SseEmitter(0L);  // 0L para evitar el timeout

        executorService.submit(() -> {
            try {
                List<DatoDistribucion> datos = distribucionDataService.obtenerDatos();
                for (DatoDistribucion dato : datos) {
                    if (dato.getCantidad() > 0 && dato.getPosicion() >= 0) {
                        emitter.send(dato);
                        Thread.sleep(delay); // Envía los datos gradualmente
                    }
                }
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

}
