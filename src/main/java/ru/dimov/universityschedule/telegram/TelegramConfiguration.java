package ru.dimov.universityschedule.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:telegram.properties")
public class TelegramConfiguration {
    @Value("${telegram.token}")
    private String token;
    @Bean
    public TelegramClient telegramClient(){
       return new OkHttpTelegramClient(token);
    }

    @Bean
    public ReplyKeyboardMarkup replyKeyboardMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows());
        //следующие три строчки могут менять значение аргументов взависимости от ваших задач
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }

    @Bean
    public List<KeyboardRow> keyboardRows() {
        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(new KeyboardRow(keyboardButtons()));
        //создаем список рядов кнопок из списка кнопок

        return rows;
    }

    @Bean
    public List<KeyboardButton> keyboardButtons() {
        List<KeyboardButton> buttons = new ArrayList<>();
        buttons.add(new KeyboardButton("Ближайшая"));
        buttons.add(new KeyboardButton("Завтра"));
        //создаем и заполняем список кнопок
        return buttons;
    }
}
