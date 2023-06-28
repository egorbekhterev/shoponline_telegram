package com.shoponline.telegram.service;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitMqDeliveryPublisher {

    private RabbitTemplate rabbitTemplate;

    public void publishDeliveryUpdate(String message) {
        rabbitTemplate.convertAndSend("deliveryQueue", message);
    }
}
