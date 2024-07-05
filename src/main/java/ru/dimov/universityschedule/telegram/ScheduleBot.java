package ru.dimov.universityschedule.telegram;


import org.springframework.data.relational.core.sql.In;
import org.telegram.telegrambots.extensions.bots.commandbot.CommandLongPollingTelegramBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.dimov.universityschedule.commands.ClassDTO;
import ru.dimov.universityschedule.commands.HelpCommand;
import ru.dimov.universityschedule.commands.ScheduleDAO;
import ru.dimov.universityschedule.commands.StartCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.dimov.universityschedule.model.TelegramUser;
import ru.dimov.universityschedule.repository.GroupAttendancesRepository;
import ru.dimov.universityschedule.repository.TelegramUserRepository;
import ru.dimov.universityschedule.sevice.EnvironmentService;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Supplier;

@Slf4j
@Component
public class ScheduleBot extends CommandLongPollingTelegramBot implements SpringLongPollingBot {

    private final GroupAttendancesRepository groupAttendancesRepository;
    private final TelegramUserRepository telegramUserRepository;
    private final String token;
    private final ReplyKeyboardMarkup replyKeyboardMarkup;
    private final ScheduleDAO scheduleDAO;
    private final EnvironmentService environmentService;

    public ScheduleBot(@Value("${telegram.token}") String token,
                       @Value("${telegram.username}")
                       String username, TelegramClient telegramClient, List<IBotCommand> commands,
                       GroupAttendancesRepository groupAttendancesRepository,
                       TelegramUserRepository telegramUserRepository,
                       ReplyKeyboardMarkup replyKeyboardMarkup,
                       ScheduleDAO scheduleDAO,
                       EnvironmentService environmentService){
        super(telegramClient,true, () -> username);
        this.token = token;
        for (IBotCommand command : commands) {
            register(command);
        }
        this.groupAttendancesRepository = groupAttendancesRepository;
        this.telegramUserRepository = telegramUserRepository;
        this.replyKeyboardMarkup = replyKeyboardMarkup;
        this.scheduleDAO = scheduleDAO;
        this.environmentService = environmentService;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }


    private void sendMessage(long chatId, String messageText){
        SendMessage message = SendMessage
                .builder()
                .replyMarkup(replyKeyboardMarkup)
                .chatId(chatId)
                .text(messageText)
                .build();
        try {
            telegramClient.execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            log.error("error", e);
            e.printStackTrace();
        }
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if(update.hasMessage() & update.getMessage().hasText()){
            String message = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            if(message.equals("Ближайшая")){
                Optional<Integer> groupId = telegramUserRepository.findById(chatId).map(TelegramUser::getGroupNumber);
                if(groupId.isPresent()) {
                    String typeOfWeek = environmentService.getTypeOfWeek();
                    ClassDTO classDTO = scheduleDAO.getNextClass(typeOfWeek.equals("верхняя"), groupId.get());
                    StringJoiner scheduleString = new StringJoiner("\n");
                    scheduleString.add("\uD83D\uDCDA" + classDTO.getSubjectType());
                    scheduleString.add(classDTO.getSubjectName());
                    scheduleString.add("Начало в: " + classDTO.getTime().toString());
                    scheduleString.add("Аудитория: %s %s".formatted(classDTO.getBuilding(), classDTO.getRoomNumber()));
                    if(classDTO.getSubjectAdditionalData()!=null){
                        scheduleString.add(classDTO.getSubjectAdditionalData());
                    }
                    if(classDTO.getTeacherName() != null) {
                        scheduleString.add("\uD83D\uDC68\u200D\uD83C\uDFEB" + "Преподователь:");
                        scheduleString.add("%s %s %s".formatted(classDTO.getTeacherSurname(), classDTO.getTeacherName(), classDTO.getTeacherPatronymic()) + "\n");
                    }
                    sendMessage(chatId, scheduleString.toString());
                }else {
                    sendMessage(chatId, "Вы не указали номер группы");
                }
            } else if (message.equals("Завтра")) {
                Optional<Integer> groupId = telegramUserRepository.findById(chatId).map(TelegramUser::getGroupNumber);
                if(groupId.isPresent()){
                    String typeOfWeek = environmentService.getTypeOfWeek();
                    List<ClassDTO> classDTO = scheduleDAO.getClasses(LocalDate.now().plusDays(1).getDayOfWeek(), typeOfWeek.equals("верхняя"), groupId.get());
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
                    sendMessage(chatId, scheduleString.toString());
                }else {
                    sendMessage(chatId, "Вы не указали номер группы");
                }

            } else {
                try {
                    Integer groupId = Integer.parseInt(message);
                    if (groupAttendancesRepository.existsByGroupAttendancesIdGroupId(groupId)) {
                        telegramUserRepository.save(new TelegramUser(chatId, groupId));
                        sendMessage(chatId, "Выбранна группа " + message);
                    } else {
                        sendMessage(chatId, "Указанной группы не существует");
                    }
                } catch (NumberFormatException e) {
                    log.error("error", e);
                    sendMessage(chatId, "Я вас не понимаю\nВоспользуйтесь коммандой /help");
                }
            }
        }
    }
}
