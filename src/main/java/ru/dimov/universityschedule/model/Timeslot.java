package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Timeslot {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "timeslot_id")
    private Long timeslotId;
    @Basic
    @Column(name = "day_of_week")
    @Convert(converter = DayOfWeekIntegerConverter.class)
    private DayOfWeek dayOfWeek;
    @Basic
    @Column(name = "time")
    private Time time;
    @Basic
    @Column(name = "upper")
    private boolean upper;

    public Timeslot(DayOfWeek dayOfWeek, Time time, boolean upper) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.upper = upper;
    }
}
