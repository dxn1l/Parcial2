package com.example.Parcial2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class SimulationConfig {

    @Bean
    public int numEstaciones() {
        return 10; // Número de estaciones de trabajo
    }

    @Bean
    public int tamañoBuffer() {
        return 10; // Tamaño del buffer compartido
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(numEstaciones());
    }

    @Bean
    public BlockingQueue<Integer> buffer() {
        return new ArrayBlockingQueue<>(tamañoBuffer());
    }
}

