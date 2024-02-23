package ru.dimov.universityschedule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class ClassGroupId implements Serializable {
    @Column(name = "class_id")
    private Long classId;
    @Column(name = "group_id")
    private Integer groupId;

    public ClassGroupId(Long classId, Integer groupId) {
        this.classId = classId;
        this.groupId = groupId;
    }

    public Long getClassId() {
        return classId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassGroupId that = (ClassGroupId) o;
        return Objects.equals(classId, that.classId) && Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classId, groupId);
    }

    @Override
    public String toString() {
        return "ClassGroupId{" +
                "classId=" + classId +
                ", groupId=" + groupId +
                '}';
    }
}
