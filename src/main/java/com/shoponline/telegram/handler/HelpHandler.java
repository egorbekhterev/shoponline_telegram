package com.shoponline.telegram.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.shoponline.telegram.components.BotCommands.HELP;

public class HelpHandler extends UpdateHandler {

    private static final String HELP_TEXT = "Will soon be written.";

    @Override
    protected boolean canHandle(Update update) {
        return super.canHandle(update) && update.getMessage().hasText() && update.getMessage().getText().equals(HELP);
    }

    @Override
    protected boolean canHandleCallback(Update update) {
        return super.canHandleCallback(update) && update.getCallbackQuery().getData().equals(HELP);
    }

    @Override
    protected BotApiMethod handle(Update update) {
        var chatId = update.getMessage().getChatId();
        return sendMessage(chatId);
    }

    @Override
    protected BotApiMethod handleCallback(Update update) {
        var chatId = update.getCallbackQuery().getMessage().getChatId();
        return sendMessage(chatId);
    }

    private BotApiMethod sendMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(HELP_TEXT);
        return message;
    }
}
