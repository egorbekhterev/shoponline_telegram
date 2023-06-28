package com.shoponline.telegram.handler;

import com.shoponline.telegram.service.RabbitMqDeliveryPublisher;
import com.shoponline.telegram.service.RabbitMqTelegramListener;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class PhoneNumberHandler extends UpdateHandler {

    private RabbitMqDeliveryPublisher rabbitMqDeliveryPublisher;

    private RabbitMqTelegramListener rabbitMqTelegramListener;

    @Override
    protected boolean canHandle(Update update) {
        return super.canHandle(update) && update.getMessage().hasContact();
    }

    @Override
    protected BotApiMethod handle(Update update) {
        var chatId = update.getMessage().getChatId();
        var phoneNumber = update.getMessage().getContact().getPhoneNumber();
        rabbitMqDeliveryPublisher.publishDeliveryUpdate(phoneNumber);
        String status = rabbitMqTelegramListener.getStatus();
        if (status == null) {
            try {
                Thread.sleep(1000);
                status = rabbitMqTelegramListener.getStatus();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format("Your package status: %s", status));
        return message;
    }
}
