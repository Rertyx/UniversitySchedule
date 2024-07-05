package ru.dimov.universityschedule.sevice;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.dimov.universityschedule.model.Teacher;
import ru.dimov.universityschedule.repository.TeacherRepository;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public Teacher addTeacherOrGet(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        try {
            return teacherRepository.findByNameAndSurnameAndPatronymic(teacher.getName(), teacher.getSurname(), teacher.getPatronymic())
                    .orElseGet(() -> teacherRepository.save(teacher));
        } catch (DataIntegrityViolationException e) {
            return addTeacherOrGet(teacher);
        }
    }
}
