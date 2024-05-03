package ru.dimov.universityschedule.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dimov.universityschedule.model.GroupAttendances;
import ru.dimov.universityschedule.model.GroupAttendancesId;

import java.util.List;

public interface GroupAttendancesRepository extends CrudRepository<GroupAttendances, GroupAttendancesId> {

    List<GroupAttendances> findByGroupAttendancesIdGroupId(Integer groupId);
}
