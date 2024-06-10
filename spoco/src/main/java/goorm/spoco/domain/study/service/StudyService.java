package goorm.spoco.domain.study.service;

import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.domain.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;

    public Study createStudy(String title) {
        Study study = Study.builder()
                .title(title)
                .build();

        return studyRepository.save(study);
    }
    public Study getStudyById(Long id) {
        return studyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Study not found with id: " + id));
    }

    public List<Study> getAllStudies() {
        return studyRepository.findAll();
    }

}
