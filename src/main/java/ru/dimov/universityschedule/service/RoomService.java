package ru.dimov.universityschedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.dimov.universityschedule.model.Room;
import ru.dimov.universityschedule.repository.RoomRepository;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Room addRoomOrGet(Room room) {
        return roomRepository.findByBuildingAndRoomNumberAndLetter(room.getBuilding(), room.getRoomNumber(), room.getLetter())
                .orElseGet(() -> roomRepository.save(room));

    }
}
