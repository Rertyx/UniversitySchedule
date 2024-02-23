package ru.dimov.universityschedule.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dimov.universityschedule.model.Subject;
import ru.dimov.universityschedule.model.Teacher;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
}
