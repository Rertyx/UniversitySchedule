package ru.dimov.universityschedule.smtu;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.DayOfWeek;
import java.util.List;

@RequiredArgsConstructor
public class WeekDay {
    private final Element weekDayElement;

    public DayOfWeek getDayOfWeek() {
        String dow = weekDayElement.select(".h5\\ my-0").first().text();
        return fromRussian(dow);
    }

    public List<LessonElement> getLessons() {
        return weekDayElement.select("tbody").select(".js-week-container\\ collapse\\ show")
                .stream()
                .map(LessonElement::new)
                .toList();

    }

    private DayOfWeek fromRussian(String dow) {
        dow = dow.toLowerCase();
        return switch (dow) {
            case "понедельник" -> DayOfWeek.MONDAY;
            case "вторник" -> DayOfWeek.TUESDAY;
            case "среда" -> DayOfWeek.WEDNESDAY;
            case "четверг" -> DayOfWeek.THURSDAY;
            case "пятница" -> DayOfWeek.FRIDAY;
            case "суббота" -> DayOfWeek.SATURDAY;
            case "воскресенье" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("неизвестный день недели");
        };

    }
}
