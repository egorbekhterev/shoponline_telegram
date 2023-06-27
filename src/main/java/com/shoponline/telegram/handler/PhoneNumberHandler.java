package com.shoponline.telegram.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class PhoneNumberHandler extends UpdateHandler {

    @Override
    protected boolean canHandle(Update update) {
        return super.canHandle(update) && update.getMessage().hasContact();
    }

    @Override
    protected BotApiMethod handle(Update update) {
        var chatId = update.getMessage().getChatId();
        var phoneNumber = update.getMessage().getContact().getPhoneNumber();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(phoneNumber);
        return message;
    }
}
