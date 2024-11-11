package com.example.Parcial2.Service;

import com.example.Parcial2.Entity.EstacionDeTrabajo;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class SchedulerService {

    private final ExecutorService executorService;

    public SchedulerService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void scheduleTasks(List<EstacionDeTrabajo> estaciones) {
        for (EstacionDeTrabajo estacion : estaciones) {
            executorService.submit(estacion); // Ejecuta en orden
        }
    }
}
