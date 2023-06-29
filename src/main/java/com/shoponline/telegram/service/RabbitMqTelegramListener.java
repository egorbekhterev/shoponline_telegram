package com.shoponline.telegram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

@Component
@Slf4j
public class RabbitMqTelegramListener {

    private String status;
    private final Semaphore messageAvailable = new Semaphore(0);
    private boolean hasMessage = false;

    @RabbitListener(queues = "telegramQueue")
    public void processTelegramUpdate(String message) {
        log.info("Message received from telegram queue: {}", message);
        status = message;
        hasMessage = true;
        messageAvailable.release();
    }

    public String getStatus() {
        try {
            messageAvailable.acquire();
            hasMessage = false;
            return status;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
