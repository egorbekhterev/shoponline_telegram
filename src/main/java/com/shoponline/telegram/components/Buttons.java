package com.shoponline.telegram.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {

    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton SHARE_BUTTON = new InlineKeyboardButton("Share number");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");

    public static InlineKeyboardMarkup inlineKeyboardMarkup() {
        HELP_BUTTON.setCallbackData("/help");
        SHARE_BUTTON.setCallbackData("/share");
        START_BUTTON.setCallbackData("/start");
        List<InlineKeyboardButton> rowInLine = List.of(SHARE_BUTTON, HELP_BUTTON, START_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInLine);
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        markupInLine.setKeyboard(rowsInLine);
        return markupInLine;
    }
}
