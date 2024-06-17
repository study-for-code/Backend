package goorm.spoco.domain.study.controller.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.category.controller.response.CategoryResponseDto;
import goorm.spoco.domain.study.domain.Study;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record StudyResponseDto (
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long studyId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String title,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long ownerId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String joinCode,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<CategoryResponseDto> categories,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime createAt

){
    public static StudyResponseDto from(Study study){
        return new StudyResponseDto(
                study.getStudyId(),
                study.getTitle(),
                study.getOwner().getMemberId(),
                study.getJoinCode(),
                study.getCategories().stream().map(CategoryResponseDto::from).collect(Collectors.toList()),
                study.getCreateAt()
        );
    }
}
