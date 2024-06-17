package goorm.spoco.domain.study.controller;

import goorm.spoco.domain.study.controller.request.StudyModifyDto;
import goorm.spoco.domain.study.controller.request.StudyRequestDto;
import goorm.spoco.domain.study.controller.response.StudyResponseDto;
import goorm.spoco.domain.study.service.StudyService;
import goorm.spoco.global.common.auth.SpocoUserDetails;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @PostMapping("/studies")
    public BaseResponse createStudy(
            @RequestBody StudyRequestDto studyRequestDto,
            @AuthenticationPrincipal SpocoUserDetails user
    ){
        return BaseResponse.builder()
                .message("스터디 생성 성공")
                .results(List.of(studyService.createStudy(studyRequestDto.title(), user.getMemberId())))
                .build();
    }

    @PatchMapping("/studies")
    public BaseResponse modifyStudy(
            @RequestBody StudyModifyDto studyModifyDto,
            @AuthenticationPrincipal SpocoUserDetails user
    ) {
        return BaseResponse.builder()
                .message("스터디 제목 수정 성공")
                .results(List.of(
                        studyService.modifyStudy(studyModifyDto, user.getMemberId())))
                .build();
    }

    @DeleteMapping("/studies/{studyId}")
    public BaseResponse deleteStudy(
            @PathVariable Long studyId,
            @AuthenticationPrincipal SpocoUserDetails user
    ) {
        return BaseResponse.builder()
                .message("스터디 삭제 성공")
                .results(List.of(studyService.deleteStudy(studyId, user.getMemberId())))
                .build();
    }

    @PostMapping("/studies/join")
    public BaseResponse joinStudy(
            @RequestParam String joinCode,
            @AuthenticationPrincipal SpocoUserDetails user
    ) {
        studyService.joinStudy(joinCode, user.getMemberId());
        return BaseResponse.builder()
                .message("스터디 입장 성공")
                .build();
    }

    @PostMapping("/studies/{studyId}/leave")
    public BaseResponse leaveStudy(
            @PathVariable Long studyId,
            @AuthenticationPrincipal SpocoUserDetails user
    ) {
        studyService.leaveStudy(studyId, user.getMemberId());
        return BaseResponse.builder()
                .message("스터디 퇴장 성공")
                .build();
    }

    @GetMapping("/studies/{studyId}")
    public BaseResponse getStudy(@PathVariable Long studyId){
        return BaseResponse.builder()
                .message("단일 스터디 정보 조회 성공")
                .results(List.of(studyService.getStudyById(studyId)))
                .build();
    }

    @GetMapping("/studies")
    public BaseResponse getAllJoinStudies(@AuthenticationPrincipal SpocoUserDetails user){
        return BaseResponse.<StudyResponseDto>builder()
                .message("활동 중인 스터디 조회 성공")
                .results(studyService.getJoinStudyList(user.getMemberId()))
                .build();
    }

}
