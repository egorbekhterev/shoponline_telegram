package com.shoponline.telegram.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Override canHandle and canHandleCallback. Implement others.
 */
public abstract class UpdateHandler {
    private UpdateHandler next;

    public UpdateHandler setNext(UpdateHandler next) {
        this.next = next;
        return next;
    }

    public BotApiMethod handleUpdate(Update update) {
        if (canHandle(update)) {
            return handle(update);
        } else if (canHandleCallback(update)) {
            return handleCallback(update);
        } else if (next != null) {
            return next.handleUpdate(update);
        }
        return null;
    }

    protected boolean canHandle(Update update) {
        return update.hasMessage();
    }

    protected boolean canHandleCallback(Update update) {
        return update.hasCallbackQuery();
    }

    protected abstract BotApiMethod handle(Update update);

    protected BotApiMethod handleCallback(Update update) {
        return null;
    }
}
