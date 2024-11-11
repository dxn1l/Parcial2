/*package com.example.Parcial2.service;


import com.example.Parcial2.Entity.EstacionDeTrabajo;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class SchedulerService {

    public void scheduleTasks(List<EstacionDeTrabajo> estaciones) {
        Flux.fromIterable(estaciones)
                .delayElements(Duration.ofMillis(100)) // Controla la frecuencia de ejecución de cada estación
                .flatMap(EstacionDeTrabajo::procesarDatos) // `procesar()` es el método de procesamiento reactivo en EstacionDeTrabajo
                .subscribe(); // Inicia la ejecución reactiva
    }
}*/

