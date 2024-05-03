package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "class_group")
public class GroupAttendances {
    @EmbeddedId
    private GroupAttendancesId groupAttendancesId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @MapsId("classId")
    private aClass aClass;

    public GroupAttendances(GroupAttendancesId groupAttendancesId, aClass aClass) {
        this.groupAttendancesId = groupAttendancesId;
        this.aClass = aClass;
    }

    public aClass getaClass() {
        return aClass;
    }

    public void setaClass(aClass aClass) {
        this.aClass = aClass;
        groupAttendancesId.setClassId(aClass.getaClassId());
    }

    public GroupAttendancesId getGroupAttendancesId() {
        return groupAttendancesId;
    }

    public void setGroupAttendancesId(GroupAttendancesId groupAttendancesId) {
        this.groupAttendancesId = groupAttendancesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupAttendances that = (GroupAttendances) o;
        return Objects.equals(getGroupAttendancesId(), that.getGroupAttendancesId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupAttendancesId());
    }

    @Override
    public String toString() {
        return "ClassGroup{" +
                "classGroupId=" + groupAttendancesId +
                '}';
    }
}
