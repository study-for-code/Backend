package goorm.spoco.domain.category.controller;

import goorm.spoco.domain.category.controller.request.CategoryRequestDto;
import goorm.spoco.domain.category.controller.response.CategoryResponseDto;
import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.category.service.CategoryService;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories/{studyId}")
    public BaseResponse createCategory(
            @RequestBody CategoryRequestDto categoryRequestDto,
            @PathVariable Long studyId
    ) {
        return BaseResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("카테고리 생성")
                .results(List.of(categoryService.save(categoryRequestDto, studyId)))
                .build();
    }

    @DeleteMapping("/categories/{categoryId}")
    public BaseResponse deleteCategory(
            @PathVariable Long categoryId
    ) {
        categoryService.delete(categoryId);
        return BaseResponse.builder()
                .message("카테고리 삭제")
                .build();
    }

    @PatchMapping("/categories/{categoryId}")
    public BaseResponse updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryRequestDto categoryRequestDto
    ) {
        return BaseResponse.builder()
                .message("카테고리 이름 업데이트")
                .results(List.of(categoryService.update(categoryId, categoryRequestDto)))
                .build();
    }

    @GetMapping("/categories/{categoryId}")
    public BaseResponse getByCategoryId(
            @PathVariable Long categoryId
    ) {
        return BaseResponse.builder()
                .message("단일 카테고리 반환 성공")
                .results(List.of(categoryService.getByCategoryId(categoryId)))
                .build();
    }

    @GetMapping("/categories/{studyId}/study")
    public BaseResponse getByStudyId(
            @PathVariable Long studyId
    ) {
        return BaseResponse.<CategoryResponseDto>builder()
                .message("스터디에 해당하는 카테고리 반환 성공")
                .results(categoryService.getAllByStudyId(studyId))
                .build();
    }
}
