package ru.dimov.universityschedule.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;
    private String building;
    @Column(name = "room_number")
    private String roomNumber;

    public Room(Long roomId, String building, String roomNumber) {
        this.roomId = roomId;
        this.building = building;
        this.roomNumber = roomNumber;
    }

    public Room(String building, String roomNumber) {
        this.building = building;
        this.roomNumber = roomNumber;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(getRoomId(), room.getRoomId()) && Objects.equals(getBuilding(), room.getBuilding()) && Objects.equals(getRoomNumber(), room.getRoomNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomId(), getBuilding(), getRoomNumber());
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", building='" + building + '\'' +
                ", roomNumber=" + roomNumber +
                '}';
    }
}
