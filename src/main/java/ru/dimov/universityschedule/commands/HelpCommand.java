package ru.dimov.universityschedule.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Collection;

@Slf4j
@Component
public class HelpCommand extends BotCommand {
    private final ICommandRegistry commandRegistry;
    public HelpCommand(@Lazy ICommandRegistry commandRegistry){
        super("help", "Команды для бота");
        this.commandRegistry = commandRegistry;
    }
    @Override
    public void execute(TelegramClient telegramClient, User user, Chat chat, String[] strings){
        StringBuilder helpMessageBuilder = new StringBuilder("<b>Help</b>\n");
        helpMessageBuilder.append("Ниже приведены существующие команды для бота\n\n");
        for (IBotCommand botCommand: commandRegistry.getRegisteredCommands()) {
            helpMessageBuilder.append(botCommand.toString()).append("\n\n");
        }

        SendMessage helpMessage = new SendMessage(chat.getId().toString(), helpMessageBuilder.toString());
        helpMessage.enableHtml(true);

        try {
            telegramClient.execute(helpMessage);
        } catch (TelegramApiException e) {
            log.error("Error", e);
        }
    }
}
