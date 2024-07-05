package ru.dimov.universityschedule.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.dimov.universityschedule.model.TelegramUser;
import ru.dimov.universityschedule.model.Timeslot;
import ru.dimov.universityschedule.repository.TelegramUserRepository;
import ru.dimov.universityschedule.sevice.EnvironmentService;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class ScheduleByGroupCommand extends BotCommand {

    private final ScheduleDAO scheduleDAO;
    private final TelegramUserRepository telegramUserRepository;
    private final EnvironmentService environmentService;
    public ScheduleByGroupCommand(ScheduleDAO scheduleDAO, TelegramUserRepository telegramUserRepository, EnvironmentService environmentService){
        super("schedule", "Присылает распиание на сегодняшний день");
        this.scheduleDAO = scheduleDAO;
        this.telegramUserRepository = telegramUserRepository;
        this.environmentService = environmentService;
    }
    @Override
    public void execute(TelegramClient telegramClient, User user, Chat chat, String[] arguments) {
        Optional<Integer> groupId = telegramUserRepository.findById(chat.getId()).map(TelegramUser::getGroupNumber);
        if(groupId.isPresent()) {
            String typeOfWeek = environmentService.getTypeOfWeek();
            //LocalDate.now().plusDays(1).getDayOfWeek();
            List<ClassDTO> classDTO = scheduleDAO.getClasses(LocalDate.now().getDayOfWeek(), typeOfWeek.equals("верхняя"), groupId.get());
            StringJoiner scheduleString = new StringJoiner("\n");
            scheduleString.add("Расписание для группы: " + groupId.get() + ", " + typeOfWeek + " " + "неделя");
            for (ClassDTO classes: classDTO) {
                scheduleString.add("\uD83D\uDCDA" + classes.getSubjectType());
                scheduleString.add(classes.getSubjectName());
                scheduleString.add("Начало в " + classes.getTime().toString());
                scheduleString.add("Аудитория: %s %s".formatted(classes.getBuilding(), classes.getRoomNumber()));
                if(classes.getSubjectAdditionalData() != null){
                    scheduleString.add("Дополнительная информация: \n" + classes.getSubjectAdditionalData());
                }
                if(classes.getTeacherName() != null) {
                    scheduleString.add("\uD83D\uDC68\u200D\uD83C\uDFEB" + "Преподователь:");
                    scheduleString.add("%s %s %s".formatted(classes.getTeacherSurname(), classes.getTeacherName(), classes.getTeacherPatronymic()));
                }
                scheduleString.add("\n");
            }

            SendMessage scheduleMessage = SendMessage
                    .builder()
                    .chatId(chat.getId())
                    .text(scheduleString.toString())
                    .build();

            try {
                telegramClient.execute(scheduleMessage);
            } catch (TelegramApiException e) {
                log.error("Error", e);
            }
        }else{
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chat.getId())
                    .text("Вы не указали номер группы")
                    .build();
            try {
                telegramClient.execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Error", e);
            }
        }

    }
}
