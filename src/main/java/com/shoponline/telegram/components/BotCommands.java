package com.shoponline.telegram.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    String START = "/start";
    String HELP = "/help";
    String SHARE = "/share";

    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand(START, "Start bot."),
            new BotCommand(HELP, "Bot info."),
            new BotCommand(SHARE, "Share phone number.")
    );
}
