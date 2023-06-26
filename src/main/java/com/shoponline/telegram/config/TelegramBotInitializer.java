package com.shoponline.telegram.config;

import com.shoponline.telegram.service.TelegramBotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@AllArgsConstructor
@Slf4j
public class TelegramBotInitializer implements InitializingBean {

    private TelegramBotService tg_bot;

    @Override
    public void afterPropertiesSet() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(tg_bot);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
