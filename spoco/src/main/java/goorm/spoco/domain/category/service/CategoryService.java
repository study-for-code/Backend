package goorm.spoco.domain.category.service;

import goorm.spoco.domain.category.controller.request.CategoryRequestDto;
import goorm.spoco.domain.category.controller.response.CategoryResponseDto;
import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.category.domain.CategoryStatus;
import goorm.spoco.domain.category.repository.CategoryRepository;
import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.domain.study.repository.StudyRepository;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final StudyRepository studyRepository;

    @Transactional
    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto, Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, studyId + "에 해당하는 스터디가 존재하지 않습니다."));

        Category category = categoryRepository.save(Category.create(categoryRequestDto.title(), study));
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .title(category.getTitle())
                .build();
    }

    @Transactional
    public void delete(Long categoryId) {
        Category category = existByCategoryId(categoryId);

        category.delete();
    }

    @Transactional
    public CategoryResponseDto update(Long categoryId, CategoryRequestDto categoryRequestDto) {
        Category category = existByCategoryId(categoryId);

        category.updateTitle(categoryRequestDto.title());
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .title(category.getTitle())
                .build();
    }

    public CategoryResponseDto getByCategoryId(Long categoryId) {
        Category category = existByCategoryId(categoryId);
        return CategoryResponseDto.from(category);
    }

    public List<CategoryResponseDto> getAllByStudyId(Long studyId) {
        return categoryRepository.findAllByStudy_StudyIdAndStatus(studyId, Status.ACTIVE)
                .stream().map(CategoryResponseDto::from).collect(Collectors.toList());
    }

    private Category existByCategoryId(Long categoryId) {
        return categoryRepository.findByCategoryIdAndStatus(categoryId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, categoryId + "에 해당하는 카테고리가 존재하지 않습니다."));
    }
}
