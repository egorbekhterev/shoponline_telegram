package com.shoponline.telegram.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.shoponline.telegram.components.BotCommands.SHARE;

public class ContactHandler extends UpdateHandler {

    @Override
    protected boolean canHandle(Update update) {
        return super.canHandle(update) && update.getMessage().hasText() && update.getMessage().getText().equals(SHARE);
    }

    @Override
    protected boolean canHandleCallback(Update update) {
        return super.canHandleCallback(update) && update.getCallbackQuery().getData().equals(SHARE);
    }

    @Override
    protected BotApiMethod handle(Update update) {
        long chatId = update.getMessage().getChatId();
        return sendMessage(chatId);
    }

    @Override
    protected BotApiMethod handleCallback(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        return sendMessage(chatId);
    }

    private BotApiMethod sendMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Enter your phone number.");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton();
        button.setRequestContact(true);
        button.setText("Share my phone number.");
        row.add(button);
        rows.add(row);
        keyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }
}
