package goorm.spoco.domain.category.controller;

import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.category.service.CategoryService;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/{studyId}/create")
    public BaseResponse createCategory(
            @RequestBody Category category,
            @PathVariable Long studyId
    ) {
        categoryService.save(category, studyId);
        return BaseResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("카테고리 생성")
                .results(List.of(category))
                .build();
    }

    @DeleteMapping("{id}/delete")
    public BaseResponse deleteCategory(
            @PathVariable Long id
    ) {
        categoryService.delete(id);
        return BaseResponse.builder()
                .message("카테고리 삭제")
                .build();
    }

    @PatchMapping("/{id}/update")
    public BaseResponse updateCategory(
            @PathVariable Long id,
            @RequestBody Category category
    ) {
        categoryService.update(id, category.getTitle());
        return BaseResponse.builder()
                .message("카테고리 이름 업데이트")
                .results(List.of(category))
                .build();
    }

    @GetMapping("/{id}")
    public BaseResponse get(
            @PathVariable Long id
    ) {
        Category category = categoryService.find(id);
        return BaseResponse.builder()
                .message("카테고리 반환")
                .results(List.of(category))
                .build();
    }
}
