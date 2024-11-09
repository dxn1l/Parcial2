package com.example.Parcial2.Service;

import java.util.concurrent.BlockingQueue;

public class EnsamblajeService implements Runnable {

    private final BlockingQueue<Integer> buffer;

    public EnsamblajeService(BlockingQueue<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Línea de ensamblaje esperando componente del buffer...");
                Integer componente = buffer.take(); // Toma un componente del buffer
                System.out.println("Línea de ensamblaje ha tomado el componente: " + componente);

                // Simula el tiempo de ensamblaje
                Thread.sleep(1000);
                System.out.println("Línea de ensamblaje ha ensamblado el componente: " + componente);
            }
        } catch (InterruptedException e) {
            System.out.println("Línea de ensamblaje fue interrumpida.");
            Thread.currentThread().interrupt(); // Manejo de la interrupción del hilo
        }
    }
}

