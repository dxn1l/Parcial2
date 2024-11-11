package com.example.Parcial2.service;

import com.example.Parcial2.Entity.DatoDistribucion;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.BlockingQueue;

public class EnsamblajeService implements Runnable {
    private final BlockingQueue<DatoDistribucion> buffer;
    private final SseEmitter emitter;

    public EnsamblajeService(BlockingQueue<DatoDistribucion> buffer, SseEmitter emitter) {
        this.buffer = buffer;
        this.emitter = emitter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                DatoDistribucion dato = buffer.take();
                emitter.send(dato);
            }
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }

    public void procesarGradual(long delay) throws InterruptedException {
        try {
            while (!buffer.isEmpty()) {
                DatoDistribucion dato = buffer.take();
                emitter.send(dato);
                Thread.sleep(delay);
            }
            emitter.complete();
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }
}
