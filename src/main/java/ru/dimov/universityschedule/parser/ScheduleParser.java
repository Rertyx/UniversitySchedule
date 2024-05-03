package ru.dimov.universityschedule.parser;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;
import ru.dimov.universityschedule.model.*;
import ru.dimov.universityschedule.repository.GroupAttendancesRepository;
import ru.dimov.universityschedule.repository.SubjectRepository;
import ru.dimov.universityschedule.repository.aClassRepository;
import ru.dimov.universityschedule.sevice.*;
import ru.dimov.universityschedule.smtu.LessonElement;
import ru.dimov.universityschedule.smtu.SchedulePage;
import ru.dimov.universityschedule.smtu.WeekDay;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleParser implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ScheduleParser.class);
    private final aClassRepository aClassRepository;
    private final GroupAttendancesRepository groupAttendancesRepository;
    private final RoomService roomService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final TimeslotService timeslotService;
    private final TransactionTemplate transactionTemplate;
    @Override
    public void run(String... arg) throws  Exception{
        List<String> allGroups = Jsoup.connect("https://www.smtu.ru/ru/listschedule/").get().
                select(".gr").select("a").eachText();

        allGroups.stream().parallel().forEach(x -> {
            try {
                processGroup(x);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }



    private void processGroup(String groupId) throws IOException{
        transactionTemplate.executeWithoutResult(TransactionStatus ->{
            List<GroupAttendances> groupAttendances = groupAttendancesRepository.findByGroupAttendancesIdGroupId(Integer.parseInt(groupId));
            List<Long> classes = groupAttendances
                    .stream().map(GroupAttendances::getaClass).map(aClass::getaClassId).toList();
            groupAttendancesRepository.deleteAllById(groupAttendances.stream().map(x -> x.getGroupAttendancesId()).toList());
            aClassRepository.deleteAllById(classes);
            log.info("Processing group {}", groupId);
            Document groupSchedule = null;
            try {
                groupSchedule = Jsoup.connect("https://www.smtu.ru/ru/viewschedule/" + groupId + "/").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SchedulePage schedulePage = new SchedulePage(groupSchedule);
            List<WeekDay> weekDays = schedulePage.getWeekDays();
            for (WeekDay weekDay : weekDays) {
                processeWeekDay(weekDay);
            }
            log.info("Finished processing group {}", groupId);
        });
    }

    private void processeWeekDay(WeekDay weekDay){
        DayOfWeek dayOfWeek = weekDay.getDayOfWeek();
        List<LessonElement> lessonElements = weekDay.getLessons();
        for (LessonElement lessonElement : lessonElements) {
            processeLesson(lessonElement, dayOfWeek);
        }
    }

    private void processeLesson(LessonElement lesson, DayOfWeek dayOfWeek){
        transactionTemplate.executeWithoutResult(TransactionStatus -> {
            List<Timeslot> timeslots = getTimeslots(lesson, dayOfWeek);
            List<Timeslot> savedTimeSlot = timeslots.stream()
                    .map(timeslotService::getTimeSlot)
                    .toList();

            Subject subject = subjectService.addSubjectOrGet(lesson.getSubject());
            Room room = roomService.addRoomOrGet(lesson.getRoom());
            Teacher teacher = teacherService.addTeacherOrGet(lesson.getTeacher());
            GroupAttendances groupAttendances = new GroupAttendances(new GroupAttendancesId(null, lesson.getGroupNumber()), null);
            for (Timeslot timeslot : savedTimeSlot) {
                List<GroupAttendances> eList = List.of(groupAttendances);
                aClass aClass = new aClass(eList, timeslot, room, subject, teacher);
                aClass save = aClassRepository.save(aClass);
                List<GroupAttendances> group =  save.getGroupAttendences().stream().peek(x -> x.setaClass(save)).toList();
                groupAttendancesRepository.saveAll(group);
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
