package ru.dimov.universityschedule.sevice;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.dimov.universityschedule.model.Room;
import ru.dimov.universityschedule.repository.RoomRepository;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Room addRoomOrGet(Room room){
        try {
            return roomRepository.findByBuildingAndRoomNumber(room.getBuilding(), room.getRoomNumber())
                    .orElseGet(() -> roomRepository.save(room));
        } catch (DataIntegrityViolationException e) {
            return addRoomOrGet(room);
        }
    }
}
