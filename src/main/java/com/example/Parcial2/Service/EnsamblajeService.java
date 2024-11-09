package com.example.Parcial2.service;

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
                Integer componente = buffer.take(); // Toma un componente del buffer
                System.out.println("Ensamblando componente: " + componente);
                // Lógica adicional de ensamblaje...
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Manejo de la interrupción del hilo
        }
    }
}
