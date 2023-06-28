package com.shoponline.telegram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMqTelegramListener {

    private String status;

    @RabbitListener(queues = "telegramQueue")
    public void processTelegramUpdate(String message) {
        log.info("Message received from telegram queue: {}", message);
        status = message;
    }

    public String getStatus() {
        return status;
    }
}
