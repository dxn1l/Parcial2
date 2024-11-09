package com.example.Parcial2.Entity;

import java.util.concurrent.BlockingQueue;

public class EstacionDeTrabajo implements Runnable {

    private final Long id;
    private final BlockingQueue<Integer> buffer;
    private final int componente;

    public EstacionDeTrabajo(Long id, BlockingQueue<Integer> buffer, int componente) {
        this.id = id;
        this.buffer = buffer;
        this.componente = componente;
    }

    @Override
    public void run() {
        try {
            System.out.println("Estación de trabajo " + id + " comenzando a producir componente: " + componente);
            Thread.sleep(500); // Simula tiempo de producción
            System.out.println("Estación de trabajo " + id + " produjo el componente: " + componente);

            System.out.println("Estación de trabajo " + id + " intentando colocar el componente en el buffer...");
            buffer.put(componente); // Coloca el componente en el buffer
            System.out.println("Estación de trabajo " + id + " ha colocado el componente " + componente + " en el buffer.");
        } catch (InterruptedException e) {
            System.out.println("Estación de trabajo " + id + " fue interrumpida.");
            Thread.currentThread().interrupt();
        }
    }
}
