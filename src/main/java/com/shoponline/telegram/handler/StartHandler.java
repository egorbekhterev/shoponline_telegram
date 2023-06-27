package com.shoponline.telegram.handler;

import com.shoponline.telegram.components.Buttons;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.shoponline.telegram.components.BotCommands.START;

public class StartHandler extends UpdateHandler {

    @Override
    protected boolean canHandle(Update update) {
        return super.canHandle(update) && update.getMessage().hasText() && update.getMessage().getText().equals(START);
    }

    @Override
    protected boolean canHandleCallback(Update update) {
        return super.canHandleCallback(update) && update.getCallbackQuery().getData().equals(START);
    }

    @Override
    protected BotApiMethod handle(Update update) {
        var chatId = update.getMessage().getChatId();
        var firstName = update.getMessage().getFrom().getFirstName();
        return sendMessage(chatId, firstName);
    }

    @Override
    protected BotApiMethod handleCallback(Update update) {
        var chatId = update.getCallbackQuery().getMessage().getChatId();
        var firstName = update.getCallbackQuery().getFrom().getFirstName();
        return sendMessage(chatId, firstName);
    }

    private BotApiMethod sendMessage(long chatId, String firstName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hello, " + firstName + "! This is notification service for shoponline. Please, share your"
                + " phone number to track your order information.");
        message.setReplyMarkup(Buttons.inlineKeyboardMarkup());
        return message;
    }
}
