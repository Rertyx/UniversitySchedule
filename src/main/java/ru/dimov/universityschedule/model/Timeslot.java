package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "timeslot")
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeslot_id")
    private Long timeslotId;
    @Convert(converter = DayOfWeekShortConverter.class)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;
    @Column(name = "time", columnDefinition = "TIME")
    private LocalTime time;
    private Boolean upper;

    public Timeslot(Long timeslotId, DayOfWeek dayOfWeek, LocalTime time, Boolean upper) {
        this.timeslotId = timeslotId;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.upper = upper;
    }

    public Timeslot(DayOfWeek dayOfWeek, LocalTime time, Boolean upper) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.upper = upper;
    }

    public Long getTimeslotId() {
        return timeslotId;
    }

    public void setTimeslotId(Long timeslotId) {
        this.timeslotId = timeslotId;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Boolean isUpper() {
        return upper;
    }

    public void setUpper(Boolean upper) {
        this.upper = upper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeslot timeslot = (Timeslot) o;
        return Objects.equals(getTimeslotId(), timeslot.getTimeslotId()) && Objects.equals(getDayOfWeek(), timeslot.getDayOfWeek()) && Objects.equals(getTime(), timeslot.getTime()) && Objects.equals(isUpper(), timeslot.isUpper());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimeslotId(), getDayOfWeek(), getTime(), isUpper());
    }

    @Override
    public String toString() {
        return "timeslot{" +
                "timeslot_id=" + timeslotId +
                ", day_of_week=" + dayOfWeek +
                ", time=" + time +
                ", upper=" + upper +
                '}';
    }
}
