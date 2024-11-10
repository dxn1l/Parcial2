package com.example.Parcial2.config;

import com.example.Parcial2.Entity.DatoDistribucion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    @Bean
    public BlockingQueue<DatoDistribucion> buffer() {
        return new ArrayBlockingQueue<>(100); // Tamaño de la cola ajustable
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10); // Número de hilos ajustable
    }
}
