package com.cordillera.ventas.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String VENTA_QUEUE = "venta_stock_queue";

    @Bean
    public Queue queue() {
        return new Queue(VENTA_QUEUE, true);
    }
}
