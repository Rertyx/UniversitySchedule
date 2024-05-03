package ru.dimov.universityschedule.smtu;

import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.relational.core.sql.In;
import ru.dimov.universityschedule.model.Room;
import ru.dimov.universityschedule.model.Subject;
import ru.dimov.universityschedule.model.Teacher;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

class LessonElementTest {
    static Element lessonDoc;

    @BeforeAll
    static void init() {
        File file = new File(LessonElementTest.class.getClassLoader().getResource("TestLesson.html").getFile());

        try {
            lessonDoc = Jsoup.parse(file).select("tr").first();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getTypeOfWeek() {
        LessonElement lessonElement = new LessonElement(lessonDoc);
        TypeOfWeek typeOfWeek = lessonElement.getTypeOfWeek();
        Assertions.assertThat(typeOfWeek).isEqualTo(TypeOfWeek.LOWER);
    }

    @Test
    void getTime() {
        LessonElement lessonElement = new LessonElement(lessonDoc);
        LocalTime localTime = lessonElement.getTime();
        Assertions.assertThat(localTime).isEqualTo(LocalTime.of(8,30));
    }

    @Test
    void getRoom() {
        LessonElement lessonElement = new LessonElement(lessonDoc);
        Room room = lessonElement.getRoom();
        Assertions.assertThat(room).isEqualTo(new Room("У", "103"));
    }

    @Test
    void getSubject(){
        LessonElement lessonElement = new LessonElement(lessonDoc);
        Subject subject = lessonElement.getSubject();
        Assertions.assertThat(subject).isEqualTo(new Subject("Высшая математика", "Лекция", null));
    }

    @Test
    void getTeacher(){
        LessonElement lessonElement = new LessonElement(lessonDoc);
        Teacher teacher = lessonElement.getTeacher();
        Assertions.assertThat(teacher).isEqualTo(new Teacher("Никита", "Ловягин", "Юрьевич", null, null, null));
    }

    @Test
    void getGroupNumber(){
        LessonElement lessonElement = new LessonElement(lessonDoc);
        Integer number = lessonElement.getGroupNumber();
        Assertions.assertThat(number).isEqualTo(1170);

    }
}