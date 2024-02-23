package ru.dimov.universityschedule.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;
import ru.dimov.universityschedule.model.Class;

@Repository()
public interface ClassRepository extends CrudRepository<Class, Long> {
}
