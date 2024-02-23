package ru.dimov.universityschedule.parser;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import ru.dimov.universityschedule.model.*;
import ru.dimov.universityschedule.model.Class;
import ru.dimov.universityschedule.repository.ClassRepository;
import ru.dimov.universityschedule.repository.GroupAttendancesRepository;
import ru.dimov.universityschedule.service.RoomService;
import ru.dimov.universityschedule.service.TeacherService;
import ru.dimov.universityschedule.service.TimeslotCachingService;
import ru.dimov.universityschedule.smtu.LessonElement;
import ru.dimov.universityschedule.smtu.SchedulePage;
import ru.dimov.universityschedule.smtu.WeekDay;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ScheduleParser implements CommandLineRunner {


    private static final Logger log = LoggerFactory.getLogger(ScheduleParser.class);
    private final TimeslotCachingService timeslotService;
    private final ClassRepository classRepository;
    private final TransactionTemplate transactionTemplate;
    private final TeacherService teacherService;
    private final RoomService roomService;
    private final GroupAttendancesRepository groupAttendancesRepository;


    @Override
    public void run(String... args) throws Exception {
        List<String> allGroups = Jsoup.connect("https://www.smtu.ru/ru/listschedule/").get()
                .select(".gr").select("a").eachText();
        allGroups.stream().parallel().forEach(x -> {
            try {
                processGroup(x);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void processGroup(String groupId) throws IOException {
        log.info("Processing group {}", groupId);
        Document groupSchedule = Jsoup.connect("https://www.smtu.ru/ru/viewschedule/" + groupId + "/").get();
        List<WeekDay> weekDays = new SchedulePage(groupSchedule).getWeekDays();
        for (WeekDay weekDay : weekDays) {
            processWeekday(weekDay);
        }
        log.info("Finished processing group {}", groupId);
    }

    private void processWeekday(WeekDay weekDay) {
        DayOfWeek dow = weekDay.getDayOfWeek();
        List<LessonElement> lessons = weekDay.getLessons();
        for (LessonElement lesson : lessons) {


            processLesson(lesson, dow);
        }
    }

    private void processLesson(LessonElement lesson, DayOfWeek dow) {

        transactionTemplate.executeWithoutResult(transactionStatus -> {
            List<Timeslot> timeslots = getTimeslots(lesson, dow);
            List<Timeslot> savedTimeslots = timeslots.stream()
                    .map(timeslotService::getTimeSlot)
                    .toList();
            Subject subject = lesson.getSubject();
            Room room = roomService.addRoomOrGet(lesson.getRoom());
            Teacher teacher = teacherService.addTeacherOrGet(lesson.getTeacher());
            GroupAttendence group = lesson.getGroupAttendence();
            for (Timeslot savedTimeslot : savedTimeslots) {
                List<GroupAttendence> eList = List.of(group);
                Class aClass = new Class(eList, teacher, room, savedTimeslot, subject);
                Class savedClass = classRepository.save(aClass);
                List<GroupAttendence> groupAttendences = savedClass.getGroupAttendences().stream().peek(x -> x.setClass(savedClass)).toList();
                groupAttendancesRepository.saveAll(groupAttendences);
            }
        });


    }

    private List<Timeslot> getTimeslots(LessonElement lesson, DayOfWeek dow) {
        return switch (lesson.getTypeOfWeek()) {
            case UPPER -> List.of(new Timeslot(dow, lesson.getTime(), true));
            case LOWER -> List.of(new Timeslot(dow, lesson.getTime(), false));
            case BOTH -> List.of(new Timeslot(dow, lesson.getTime(), true), new Timeslot(dow, lesson.getTime(), false));
        };
    }


}
