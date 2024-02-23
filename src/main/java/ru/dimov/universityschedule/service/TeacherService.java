package ru.dimov.universityschedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.dimov.universityschedule.model.Room;
import ru.dimov.universityschedule.model.Teacher;
import ru.dimov.universityschedule.repository.TeacherRepository;
@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Teacher addTeacherOrGet(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        return teacherRepository.findByNameAndSurnameAndPatronymic(teacher.getName(), teacher.getSurname(), teacher.getPatronymic())
                .orElseGet(() -> teacherRepository.save(teacher));

    }
}
