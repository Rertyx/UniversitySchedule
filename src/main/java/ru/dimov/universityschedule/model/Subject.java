package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;

    private String name;

    private String type;

    @Column(name = "additional_data")
    private String additionalData;

    public Subject(Long subjectId, String name, String type, String additionalData) {
        this.subjectId = subjectId;
        this.name = name;
        this.type = type;
        this.additionalData = additionalData;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(getSubjectId(), subject.getSubjectId()) && Objects.equals(getName(), subject.getName()) && Objects.equals(getType(), subject.getType()) && Objects.equals(getAdditionalData(), subject.getAdditionalData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubjectId(), getName(), getType(), getAdditionalData());
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", additionalData='" + additionalData + '\'' +
                '}';
    }
}
