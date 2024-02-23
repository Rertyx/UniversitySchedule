package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "subject_id")
    private long subjectId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "additional_data")
    private String additionalData;

    public Subject(String name, String type, String additionalData) {
        this.name = name;
        this.type = type;
        this.additionalData = additionalData;
    }

}
