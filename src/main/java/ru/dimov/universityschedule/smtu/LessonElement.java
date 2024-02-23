package ru.dimov.universityschedule.smtu;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.dimov.universityschedule.model.GroupAttendence;
import ru.dimov.universityschedule.model.Room;
import ru.dimov.universityschedule.model.Subject;
import ru.dimov.universityschedule.model.Teacher;

import java.sql.Time;

public class LessonElement {
    private static final String WEEK_BOTH_ATTR = "week-both-container";
    private static final String WEEK_UP_ATTR = "week-up-container";
    private static final String WEEK_DOWN_ATTR = "week-down-container";

    private final Element element;
    private final Elements tdElements;

    public LessonElement(Element element) {
        this.element = element;
        this.tdElements = element.select("td");
    }

    public TypeOfWeek getTypeOfWeek() {
        Attribute idAttribute = element.attribute("id");
        String attributeOfUpperWeek = idAttribute.getValue();
        return getTypeOfWeek(attributeOfUpperWeek);
    }

    public Time getTime() {
        Element timeElement = element.select("th").first();
        String time = timeElement.text().split("-")[0];
        return Time.valueOf(time + ":00");
    }

    public Room getRoom() {
        String text = tdElements.get(1).text();
        String[] splitted = text.split(" ");
        String roomNumberWithChar = splitted[1];
        String roomNumber;
        String letter = null;
        if (roomNumberWithChar.charAt(roomNumberWithChar.length() - 1) > 57) {
            roomNumber = roomNumberWithChar.substring(0, roomNumberWithChar.length() - 1);
            letter = roomNumberWithChar.substring(roomNumberWithChar.length() - 1);
        } else {
            roomNumber = roomNumberWithChar;
        }
        return new Room(splitted[0], roomNumber, letter);
    }

    public GroupAttendence getGroupAttendence() {
        String groupId = tdElements.get(2).text();
        return new GroupAttendence(new GroupAttendence.Key(null, Integer.parseInt(groupId)), null);
    }

    public Subject getSubject() {
        Element subjectElement = tdElements.get(3);
        String name = subjectElement.select("span").first().text();

        Elements smallElements = subjectElement.select("small");
        String lessonType = smallElements.first().text();
        String additionalData = null;
        if (smallElements.size() > 1) {
            additionalData = smallElements.get(1).text();
        }
        return new Subject(name, lessonType, additionalData);
    }

    public Teacher getTeacher() {
        Element teacherElement = tdElements.get(4);
        Element span = teacherElement.select("span").first();
        if (span != null) {
            String[] fio = span.text().split(" ");
            return getTeacher(fio);
        }else {
            Element a = teacherElement.select("a").first();
            if (a != null) {
                String[] fio = a.text().split(" ");
                return getTeacher(fio);
            }
        }
        return null;

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

    private TypeOfWeek getTypeOfWeek(String attributeOfUpperWeek) {
        return switch (attributeOfUpperWeek) {
            case WEEK_BOTH_ATTR -> TypeOfWeek.BOTH;
            case WEEK_UP_ATTR -> TypeOfWeek.UPPER;
            case WEEK_DOWN_ATTR -> TypeOfWeek.LOWER;
            default -> throw new IllegalArgumentException("unknow attribute: " + attributeOfUpperWeek);
        };
    }


}
