package ru.dimov.universityschedule.smtu;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.DayOfWeek;
import java.util.List;

@RequiredArgsConstructor
public class WeekDay {
    private final Element element;

    public DayOfWeek getDayOfWeek(){
        String dayOfWeek  = element.select(".h5\\ my-0").first().text();
        return fromRussian(dayOfWeek);
    }

    public List<LessonElement> getLessons(){
        return element.select("tbody").select(".js-week-container\\ collapse\\ show").stream().map(LessonElement::new).toList();
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
