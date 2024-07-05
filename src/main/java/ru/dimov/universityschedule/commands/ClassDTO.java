package ru.dimov.universityschedule.commands;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ClassDTO {

    private LocalTime time;
    private String roomNumber;
    private String building;
    private String subjectName;
    private String subjectType;
    private String subjectAdditionalData;
    private String teacherName;
    private String teacherSurname;
    private String teacherPatronymic;

    public LocalTime getTime() {
        return this.time;
    }

    public String getRoomNumber() {
        return this.roomNumber;
    }

    public String getBuilding() {
        return this.building;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public String getSubjectType() {
        return this.subjectType;
    }

    public String getSubjectAdditionalData() {
        return this.subjectAdditionalData;
    }

    public String getTeacherName() {
        return this.teacherName;
    }

    public String getTeacherSurname() {
        return Objects.requireNonNullElse(teacherSurname, "");
    }

    public String getTeacherPatronymic() {
        return Objects.requireNonNullElse(teacherPatronymic, "");
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public void setSubjectAdditionalData(String subjectAdditionalData) {
        this.subjectAdditionalData = subjectAdditionalData;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setTeacherSurname(String teacherSurname) {
        this.teacherSurname = teacherSurname;
    }

    public void setTeacherPatronymic(String teacherPatronymic) {
        this.teacherPatronymic = teacherPatronymic;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ClassDTO)) return false;
        final ClassDTO other = (ClassDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$time = this.getTime();
        final Object other$time = other.getTime();
        if (this$time == null ? other$time != null : !this$time.equals(other$time)) return false;
        final Object this$roomNumber = this.getRoomNumber();
        final Object other$roomNumber = other.getRoomNumber();
        if (this$roomNumber == null ? other$roomNumber != null : !this$roomNumber.equals(other$roomNumber))
            return false;
        final Object this$building = this.getBuilding();
        final Object other$building = other.getBuilding();
        if (this$building == null ? other$building != null : !this$building.equals(other$building)) return false;
        final Object this$subjectName = this.getSubjectName();
        final Object other$subjectName = other.getSubjectName();
        if (this$subjectName == null ? other$subjectName != null : !this$subjectName.equals(other$subjectName))
            return false;
        final Object this$subjectType = this.getSubjectType();
        final Object other$subjectType = other.getSubjectType();
        if (this$subjectType == null ? other$subjectType != null : !this$subjectType.equals(other$subjectType))
            return false;
        final Object this$subjectAdditionalData = this.getSubjectAdditionalData();
        final Object other$subjectAdditionalData = other.getSubjectAdditionalData();
        if (this$subjectAdditionalData == null ? other$subjectAdditionalData != null : !this$subjectAdditionalData.equals(other$subjectAdditionalData))
            return false;
        final Object this$teacherName = this.getTeacherName();
        final Object other$teacherName = other.getTeacherName();
        if (this$teacherName == null ? other$teacherName != null : !this$teacherName.equals(other$teacherName))
            return false;
        final Object this$teacherSurname = this.getTeacherSurname();
        final Object other$teacherSurname = other.getTeacherSurname();
        if (this$teacherSurname == null ? other$teacherSurname != null : !this$teacherSurname.equals(other$teacherSurname))
            return false;
        final Object this$teacherPatronymic = this.getTeacherPatronymic();
        final Object other$teacherPatronymic = other.getTeacherPatronymic();
        if (this$teacherPatronymic == null ? other$teacherPatronymic != null : !this$teacherPatronymic.equals(other$teacherPatronymic))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ClassDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $time = this.getTime();
        result = result * PRIME + ($time == null ? 43 : $time.hashCode());
        final Object $roomNumber = this.getRoomNumber();
        result = result * PRIME + ($roomNumber == null ? 43 : $roomNumber.hashCode());
        final Object $building = this.getBuilding();
        result = result * PRIME + ($building == null ? 43 : $building.hashCode());
        final Object $subjectName = this.getSubjectName();
        result = result * PRIME + ($subjectName == null ? 43 : $subjectName.hashCode());
        final Object $subjectType = this.getSubjectType();
        result = result * PRIME + ($subjectType == null ? 43 : $subjectType.hashCode());
        final Object $subjectAdditionalData = this.getSubjectAdditionalData();
        result = result * PRIME + ($subjectAdditionalData == null ? 43 : $subjectAdditionalData.hashCode());
        final Object $teacherName = this.getTeacherName();
        result = result * PRIME + ($teacherName == null ? 43 : $teacherName.hashCode());
        final Object $teacherSurname = this.getTeacherSurname();
        result = result * PRIME + ($teacherSurname == null ? 43 : $teacherSurname.hashCode());
        final Object $teacherPatronymic = this.getTeacherPatronymic();
        result = result * PRIME + ($teacherPatronymic == null ? 43 : $teacherPatronymic.hashCode());
        return result;
    }

    public String toString() {
        return "ClassDTO(time=%s, roomNumber=%s, building=%s, subjectName=%s, subjectType=%s, subjectAdditionalData=%s, teacherName=%s, teacherSurname=%s, teacherPatronymic=%s)".formatted(this.getTime(),
                this.getRoomNumber(),
                this.getBuilding(),
                this.getSubjectName(),
                this.getSubjectType(),
                this.getSubjectAdditionalData(),
                this.getTeacherName(),
                this.getTeacherSurname(),
                this.getTeacherPatronymic());
    }
}
