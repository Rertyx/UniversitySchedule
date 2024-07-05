package ru.dimov.universityschedule.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Arrays;

@Slf4j
@Component
public class StartCommand extends BotCommand{

    public StartCommand(){
        super("start", "Команда для запуска бота");
    }

    @Override
    public void execute(TelegramClient telegramClient, User user, Chat chat, String[] strings){
        SendMessage startMessage = SendMessage
                .builder()
                .chatId(chat.getId().toString())
                .text("""
                        \uD83D\uDC4B Привет, студент!!! Я бот, которого создали для того,
                        чтобы тебе было удобнее следить за своим \u23F0расписанием.
                        
                        \uD83E\uDD16Я могу атоматически определять текущуй день недели
                        и предоставлять актуальное расписание.
                        
                        \u2757Для выбора группы просто напиши ее номер в чат.
                        \uD83D\uDD04Для того, чтобы сменить группу еще раз напиши ее номер боту.
                        
                        \u25B6Кнопка "Ближайшая" отправит ближайшую пару по расписанию.
                        \u25B6Кнопка "Завтра" отправит расписание на завтрашний день.
                        
                        \u2753Чтобы узнать больше - воспользуйся командой /help.
                        """)
                .build();

        try {
            telegramClient.execute(startMessage);
        } catch (TelegramApiException e) {
            log.error("Error", e);
        }
    }

}
