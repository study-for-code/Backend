package goorm.spoco.domain.category.service;

import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.category.repository.CategoryRepository;
import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.domain.study.repository.StudyRepository;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final StudyRepository studyRepository;

    public Category save(Category category, Long studyId) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, studyId + "에 해당하는 스터디가 존재하지 않습니다."));
        Optional<Category> optionalCategory = categoryRepository.findByTitleAndStudy(category.getTitle(), study);
        if (optionalCategory.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, optionalCategory.get().getTitle() + "와 같은 이름의 카테고리가 존재합니다.");
        }
        category.addStudy(study);
        return categoryRepository.save(category);
    }
}
