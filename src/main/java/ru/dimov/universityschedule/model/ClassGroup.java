package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "class_group")
public class ClassGroup {
    @EmbeddedId
    private ClassGroupId classGroupId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @MapsId("classId")
    private aClass aClass;

    public ClassGroup(ClassGroupId classGroupId, aClass aClass) {
        this.classGroupId = classGroupId;
        this.aClass = aClass;
    }

    public aClass getaClass() {
        return aClass;
    }

    public void setaClass(aClass aClass) {
        this.aClass = aClass;
    }

    public ClassGroupId getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(ClassGroupId classGroupId) {
        this.classGroupId = classGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassGroup that = (ClassGroup) o;
        return Objects.equals(getClassGroupId(), that.getClassGroupId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClassGroupId());
    }

    @Override
    public String toString() {
        return "ClassGroup{" +
                "classGroupId=" + classGroupId +
                '}';
    }
}
