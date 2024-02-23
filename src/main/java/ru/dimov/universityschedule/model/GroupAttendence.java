package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import java.io.Serializable;

@Data
@Entity
@Table(name = "class_group")
@AllArgsConstructor
@NoArgsConstructor
public class GroupAttendence {
    @EmbeddedId
    private Key key;

    @JoinColumn(name = "class_id")
    @MapsId("classId")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Class aClass;

    public void setClass(Class aClass) {
        this.aClass = aClass;
        this.key.setClassId(aClass.getId());
    }

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Key implements Serializable {
        @Column(name = "class_id")
        private Long classId;
        @Column(name = "group_id")
        private Integer groupId;
    }
}
