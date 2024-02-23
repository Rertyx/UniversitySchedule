package ru.dimov.universityschedule.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dimov.universityschedule.model.Room;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Long> {
    Optional<Room> findByBuildingAndRoomNumberAndLetter(String building, String roomNumber, String letter);
}
