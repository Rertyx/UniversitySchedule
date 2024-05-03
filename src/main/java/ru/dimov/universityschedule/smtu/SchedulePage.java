package ru.dimov.universityschedule.smtu;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

@RequiredArgsConstructor
public class SchedulePage {
    private final Document document;

    public List<WeekDay> getWeekDays(){
        Elements weelDayElements = document.select(".card\\ my-4");
        return weelDayElements.stream().map(WeekDay::new).toList();
    }
}
