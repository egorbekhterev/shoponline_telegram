package com.shoponline.telegram.service;

import com.shoponline.telegram.components.BotCommands;
import com.shoponline.telegram.config.TelegramBotConfig;
import com.shoponline.telegram.handler.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBotService extends TelegramLongPollingBot implements BotCommands {

    private final TelegramBotConfig config;

    private RabbitMqDeliveryPublisher rabbitMqDeliveryPublisher;

    private RabbitMqTelegramListener rabbitMqTelegramListener;

    public TelegramBotService(@Value("${tg.token}") String botToken, TelegramBotConfig config,
                              RabbitMqDeliveryPublisher rabbitMqDeliveryPublisher,
                              RabbitMqTelegramListener rabbitMqTelegramListener) {
        super(botToken);
        this.config = config;
        this.rabbitMqDeliveryPublisher = rabbitMqDeliveryPublisher;
        this.rabbitMqTelegramListener = rabbitMqTelegramListener;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        UpdateHandler startHandler = new StartHandler();
        UpdateHandler helpHandler = new HelpHandler();
        UpdateHandler contactHandler = new ContactHandler();
        UpdateHandler phoneNumberHandler = new PhoneNumberHandler(rabbitMqDeliveryPublisher, rabbitMqTelegramListener);
        startHandler.setNext(helpHandler).setNext(contactHandler).setNext(phoneNumberHandler);
        var msg = startHandler.handleUpdate(update);
        send(msg);
    }

    private void send(BotApiMethod msg) {
        try {
            execute(msg);
            log.info("Reply sent. {}", msg.toString());
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
