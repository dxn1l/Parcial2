package com.example.Parcial2.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue csvQueue() {
        return new Queue("csvQueue", false);
    }

    @Bean
    public Queue importQueue() {
        return new Queue("importQueue", false);
    }

    @Bean
    public Queue processQueue() {
        return new Queue("processQueue", false);
    }

    @Bean
    public Queue simulateQueue() {
        return new Queue("simulateQueue", false);
    }
}