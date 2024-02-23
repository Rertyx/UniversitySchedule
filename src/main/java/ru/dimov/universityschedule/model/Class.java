package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @Id
    @Column(name = "class_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "aClass")
    private List<GroupAttendence> groupAttendences;
    @JoinColumn(name = "teacher_id")
    @OneToOne
    private Teacher teacher;
    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "timeslot_id")
    private Timeslot timeslot;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Class(List<GroupAttendence> groupAttendences, Teacher teacher, Room room, Timeslot timeslot, Subject subject) {
        this.groupAttendences = groupAttendences;
        this.teacher = teacher;
        this.room = room;
        this.timeslot = timeslot;
        this.subject = subject;
    }
}
