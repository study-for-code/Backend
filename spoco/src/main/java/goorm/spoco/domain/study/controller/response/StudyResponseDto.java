package goorm.spoco.domain.study.controller.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.study.domain.Study;
import lombok.Builder;

import java.time.LocalDateTime;

public record StudyResponseDto (
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long studyId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String title,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long ownerId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime createAt

){
    public static StudyResponseDto from(Study study){
        return new StudyResponseDto(
                study.getStudyId(),
                study.getTitle(),
                study.getOwner().getMemberId(),
                study.getCreateAt()
        );
    }
}
