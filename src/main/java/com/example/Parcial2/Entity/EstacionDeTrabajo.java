package com.example.Parcial2.Entity;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import reactor.core.publisher.Flux;
import java.time.Duration;

public class EstacionDeTrabajo {
    private final Long id;
    private final BlockingQueue<DatoDistribucion> buffer;
    private final List<DatoDistribucion> datosCSV;

    public EstacionDeTrabajo(Long id, BlockingQueue<DatoDistribucion> buffer, List<DatoDistribucion> datosCSV) {
        this.id = id;
        this.buffer = buffer;
        this.datosCSV = datosCSV;
    }

    // Método que devuelve un Flux para emitir los datos de forma reactiva
    public Flux<DatoDistribucion> procesarDatos() {
        return Flux.fromIterable(datosCSV)
                .delayElements(Duration.ofMillis(100)) // Ajusta el retraso según sea necesario
                .doOnNext(dato -> {
                    try {
                        buffer.put(dato);
                        System.out.println("Estación " + id + " procesó dato: " + dato);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
    }
}



