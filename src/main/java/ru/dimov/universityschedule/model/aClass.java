package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "class")
public class aClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long aClassId;

    @OneToMany(mappedBy = "aClass", cascade = CascadeType.REMOVE)
    private List<GroupAttendances> groupAttendences;
    @ManyToOne
    @JoinColumn(name = "timeslot_id")
    private Timeslot timeslot;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public aClass(Long aClassId, Timeslot timeslot, Room room, Subject subject, Teacher teacher) {
        this.aClassId = aClassId;
        this.timeslot = timeslot;
        this.room = room;
        this.subject = subject;
        this.teacher = teacher;
    }

    public aClass(List<GroupAttendances> groupAttendences, Timeslot timeslot, Room room, Subject subject, Teacher teacher) {
        this.groupAttendences = groupAttendences;
        this.timeslot = timeslot;
        this.room = room;
        this.subject = subject;
        this.teacher = teacher;
    }

    public Long getaClassId() {
        return aClassId;
    }

    public void setaClassId(Long aClassId) {
        this.aClassId = aClassId;
    }

    public List<GroupAttendances> getGroupAttendences() {
        return groupAttendences;
    }

    public void setGroupAttendences(List<GroupAttendances> groupAttendences) {
        this.groupAttendences = groupAttendences;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslotId) {
        this.timeslot = timeslotId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room roomId) {
        this.room = roomId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subjectId) {
        this.subject = subjectId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacherId) {
        this.teacher = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        aClass aClass = (aClass) o;
        return Objects.equals(getaClassId(), aClass.getaClassId()) && Objects.equals(getTimeslot(), aClass.getTimeslot()) && Objects.equals(getRoom(), aClass.getRoom()) && Objects.equals(getSubject(), aClass.getSubject()) && Objects.equals(getTeacher(), aClass.getTeacher());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getaClassId(), getTimeslot(), getRoom(), getSubject(), getTeacher());
    }

    @Override
    public String toString() {
        return "aClass{" +
                "aClassId=" + aClassId +
                ", timeslotId=" + timeslot +
                ", roomId=" + room +
                ", subjectId=" + subject +
                ", teacherId=" + teacher +
                '}';
    }
}
