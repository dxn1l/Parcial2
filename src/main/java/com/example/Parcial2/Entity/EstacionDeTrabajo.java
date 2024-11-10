package com.example.Parcial2.Entity;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class EstacionDeTrabajo implements Runnable {
    private final Long id;
    private final BlockingQueue<DatoDistribucion> buffer;
    private final List<DatoDistribucion> datosAsignados;

    public EstacionDeTrabajo(Long id, BlockingQueue<DatoDistribucion> buffer, List<DatoDistribucion> datosAsignados) {
        this.id = id;
        this.buffer = buffer;
        this.datosAsignados = datosAsignados;
    }

    @Override
    public void run() {
        for (DatoDistribucion dato : datosAsignados) {
            try {
                buffer.put(dato);
                Thread.sleep(200); // Ajusta para ralentizar la producción
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
