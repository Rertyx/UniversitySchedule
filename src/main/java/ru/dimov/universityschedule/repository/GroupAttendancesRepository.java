package ru.dimov.universityschedule.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dimov.universityschedule.model.GroupAttendence;

public interface GroupAttendancesRepository extends CrudRepository<GroupAttendence, GroupAttendence.Key> {
}
