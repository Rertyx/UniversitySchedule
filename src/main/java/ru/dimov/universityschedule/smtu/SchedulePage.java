package ru.dimov.universityschedule.smtu;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

@RequiredArgsConstructor
public class SchedulePage {
    private final Document mainDocument;


    public List<WeekDay> getWeekDays() {
        Elements weekDayElements = mainDocument.select(".card\\ my-4");
        return weekDayElements.stream().map(WeekDay::new).toList();
    }
}
