package ru.dimov.universityschedule.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.dimov.universityschedule.model.Subject;
import ru.dimov.universityschedule.repository.SubjectRepository;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public Subject addSubjectOrGet(Subject subject){
        try {
            return subjectRepository.findByNameAndTypeAndAdditionalData(subject.getName(), subject.getType(), subject.getAdditionalData()).orElseGet(() -> subjectRepository.save(subject));
        } catch (DataIntegrityViolationException e) {
            return addSubjectOrGet(subject);
        }
    }
}
