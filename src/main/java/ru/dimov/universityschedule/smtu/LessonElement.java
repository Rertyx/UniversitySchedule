package ru.dimov.universityschedule.smtu;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Attribute;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import ru.dimov.universityschedule.model.Room;
import ru.dimov.universityschedule.model.Subject;
import ru.dimov.universityschedule.model.Teacher;

import java.time.DayOfWeek;
import java.time.LocalTime;


public class LessonElement {
    private static final String WEEK_BOTH_ATTR = "week-both-container";
    private static final String WEEK_UP_ATTR = "week-up-container";
    private static final String WEEK_DOWN_ATTR = "week-down-container";

    private final Element element;
    private final Elements tdElements;

    public LessonElement (Element element){
        this.element = element;
        this.tdElements = element.select("td");
    }

    public TypeOfWeek getTypeOfWeek (){
        Attribute attribute = element.attribute("id");
        String attributeOfWeek = attribute.getValue();
        return getTypeOfWeek(attributeOfWeek);
    }

    public LocalTime getTime(){
        String[] time = element.select("th").first().text().split( "-")[0].split(":");
        return LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
    }

    public Room getRoom(){
        String roomText = tdElements.get(1).text();
        int spaceIndex = roomText.indexOf(" ");
        String building = roomText.substring(0, spaceIndex);
        String roomNumber = roomText.substring(spaceIndex + 1);
        return new Room( building, roomNumber);
    }

    public Subject getSubject(){
        Element subjectElements = tdElements.get(3);
        String name = subjectElements.select("span").first().text();
        Elements smallElements = subjectElements.select("small");
        String additionalData = null;
        String type = smallElements.first().text();
        if(smallElements.size() > 1) {
            additionalData = smallElements.get(1).text();
        }
        return new Subject(name, type, additionalData);
    }

    public Teacher getTeacher(){
        Element teacherElemet = tdElements.get(4).select("a").first();
        if(teacherElemet == null){
            teacherElemet = tdElements.get(4).select("span").first();
        }
        if (teacherElemet == null){
            return null;
        }
        String[] teacherData = teacherElemet.text().split(" ");
        return getTeacher(teacherData);
    }

    private static Teacher getTeacher(String[] fio) {
        String surName = fio[0];
        String name = fio[1];
        String patronymic = null;
        if (fio.length > 2) {
            patronymic = fio[2];
        }

        return new Teacher(name, surName, patronymic, null, null, null);
    }

    public Integer getGroupNumber(){
        return Integer.parseInt(tdElements.get(2).text());
    }

    private TypeOfWeek getTypeOfWeek(String attributeOfUpperWeek) {
        return switch (attributeOfUpperWeek) {
            case WEEK_BOTH_ATTR -> TypeOfWeek.BOTH;
            case WEEK_UP_ATTR -> TypeOfWeek.UPPER;
            case WEEK_DOWN_ATTR -> TypeOfWeek.LOWER;
            default -> throw new IllegalArgumentException("unknow attribute: " + attributeOfUpperWeek);
        };
    }
}
