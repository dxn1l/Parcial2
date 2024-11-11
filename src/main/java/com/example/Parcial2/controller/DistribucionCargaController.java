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
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RestController
public class DistribucionCargaController {

    private final DistribucionDataService distribucionDataService;
    private final RabbitTemplate rabbitTemplate;
    private final EnsamblajeService ensamblajeService;
    private final int numEstaciones = 10;

    public DistribucionCargaController(DistribucionDataService distribucionDataService,
                                       RabbitTemplate rabbitTemplate,
                                       EnsamblajeService ensamblajeService) {
        this.distribucionDataService = distribucionDataService;
        this.rabbitTemplate = rabbitTemplate;
        this.ensamblajeService = ensamblajeService;
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
    ensamblajeService.setEmitter(emitter);

    List<DatoDistribucion> datos = distribucionDataService.obtenerDatos();
    int numDatosPorEstacion = (int) Math.ceil((double) datos.size() / numEstaciones);

    for (int i = 0; i < numEstaciones; i++) {
        int start = i * numDatosPorEstacion;
        int end = Math.min(start + numDatosPorEstacion, datos.size());
        if (start < datos.size()) {
            List<DatoDistribucion> subListaDatos = datos.subList(start, end);
            EstacionDeTrabajo estacion = new EstacionDeTrabajo((long) i, subListaDatos, rabbitTemplate);
            estacion.procesarDatosConRabbitMQ(delay);  // Enviar datos a RabbitMQ en lugar del buffer
        }
    }

    return emitter;
}

}
