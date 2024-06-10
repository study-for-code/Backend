package goorm.spoco.domain.study.controller.response;


import goorm.spoco.domain.study.controller.request.CreateStudyReq;
import goorm.spoco.domain.study.domain.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudyRes {
    private Long id;
    private String title;
    private LocalDateTime createAt;

    public static CreateStudyRes fromEntity(Study study){
        return new CreateStudyRes(study.getStudyId(), study.getTitle(), study.getCreateAt());
    }
}
