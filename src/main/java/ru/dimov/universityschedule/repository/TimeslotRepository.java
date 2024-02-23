package ru.dimov.universityschedule.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dimov.universityschedule.model.Timeslot;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Locale;
import java.util.Optional;

public interface TimeslotRepository extends CrudRepository<Timeslot, Long> {
    Optional<Timeslot> findByDayOfWeekAndTimeAndUpper(DayOfWeek dayOfWeek, Time time, boolean upper);
}
