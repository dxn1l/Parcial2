package com.example.Parcial2.Entity;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class EstacionDeTrabajo implements Runnable {
    private final Long id;
    private final BlockingQueue<DatoDistribucion> buffer;
    private final List<DatoDistribucion> datosCSV;

    public EstacionDeTrabajo(Long id, BlockingQueue<DatoDistribucion> buffer, List<DatoDistribucion> datosCSV) {
        this.id = id;
        this.buffer = buffer;
        this.datosCSV = datosCSV;
    }

    @Override
    public void run() {
        datosCSV.forEach(dato -> {
            try {
                buffer.put(dato); // Añadir el dato del CSV al buffer directamente
                System.out.println("Estación " + id + " produjo dato: " + dato);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}


