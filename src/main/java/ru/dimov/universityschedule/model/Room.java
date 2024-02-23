package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "room_id")
    private Long roomId;
    @Basic
    @Column(name = "building")
    private String building;
    @Basic
    @Column(name = "room_number")
    private String roomNumber;
    @Basic
    @Column(name = "letter")
    private String letter;

    public Room(String building, String roomNumber, String letter) {
        this.building = building;
        this.roomNumber = roomNumber;
        this.letter = letter;
    }
}
