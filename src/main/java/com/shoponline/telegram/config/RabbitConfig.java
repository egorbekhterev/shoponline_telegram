package com.shoponline.telegram.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue deliveryQueue() {
        return new Queue("deliveryQueue", true);
    }
}
