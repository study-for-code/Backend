package goorm.spoco.domain.study.controller.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.category.controller.response.CategoryResponseDto;
import goorm.spoco.domain.image.controller.response.ImageResponseDto;
import goorm.spoco.domain.image.domain.Image;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.global.common.response.Status;
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

        ImageResponseDto image,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<CategoryResponseDto> categories,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<MemberResponseDto> members,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime createAt

){
    public static StudyResponseDto from(Study study){
        return new StudyResponseDto(
                study.getStudyId(),
                study.getTitle(),
                study.getOwner().getMemberId(),
                study.getJoinCode(),
                ImageResponseDto.from(study.getImage()),
                study.getCategories().stream().map(CategoryResponseDto::from).collect(Collectors.toList()),
                study.getJoins().stream()
                        .filter(join -> join.getStatus().equals(Status.ACTIVE))
                        .map(join -> MemberResponseDto.from(join.getMember()))
                        .collect(Collectors.toList()),
                study.getCreateAt()
        );
    }

    public static StudyResponseDto all(Study study) {
        return new StudyResponseDto(
                study.getStudyId(),
                study.getTitle(),
                study.getOwner().getMemberId(),
                study.getJoinCode(),
                ImageResponseDto.from(study.getImage()),
                study.getCategories().stream().map(CategoryResponseDto::from).collect(Collectors.toList()),
                null,
                study.getCreateAt()
        );
    }
}
