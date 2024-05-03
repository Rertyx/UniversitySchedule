package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long teacherId;

    private String name;
    private String surname;
    private String patronymic;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    private String photo;

    public Teacher(Long teacherId, String name, String surname, String patronymic, String phoneNumber, String email, String photo) {
        this.teacherId = teacherId;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.photo = photo;
    }

    public Teacher(String name, String surname, String patronymic, String phoneNumber, String email, String photo) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.photo = photo;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getTeacherId(), teacher.getTeacherId()) && Objects.equals(getName(), teacher.getName()) && Objects.equals(getSurname(), teacher.getSurname()) && Objects.equals(getPatronymic(), teacher.getPatronymic()) && Objects.equals(getPhoneNumber(), teacher.getPhoneNumber()) && Objects.equals(getEmail(), teacher.getEmail()) && Objects.equals(getPhoto(), teacher.getPhoto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeacherId(), getName(), getSurname(), getPatronymic(), getPhoneNumber(), getEmail(), getPhoto());
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
