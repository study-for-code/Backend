package goorm.spoco.domain.member.controller;

import goorm.spoco.domain.member.controller.request.MemberModifyDto;
import goorm.spoco.domain.member.controller.request.MemberSignUpDto;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.service.MemberService;
import goorm.spoco.global.common.auth.SpocoUserDetails;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 가입
     * @RequestBody : memberRequestDto
     */
    @PostMapping("/members")
    public BaseResponse signUp(@RequestBody MemberSignUpDto memberSignUpDto) {
        return BaseResponse.builder()
                .message("회원 가입 성공")
                .results(List.of(memberService.create(memberSignUpDto)))
                .build();
    }

    /**
     * 회원 수정
     * @PathVariable : memberId
     * @RequestBody : memberModifyDto
     */
    @PatchMapping("/members")
    public BaseResponse memberModify(
            @RequestBody MemberModifyDto memberModifyDto,
            Authentication authentication
    ) {
        return BaseResponse.builder()
                .message("회원 수정 성공")
                .results(List.of(memberService.modify(memberModifyDto, authentication)))
                .build();
    }

    /**
     * 회원 삭제
     * @PathVariable : memberId
     */
    @DeleteMapping("/members/{memberId}")
    public BaseResponse memberDelete(
            @PathVariable Long memberId,
            Authentication authentication
    ) {
        return BaseResponse.builder()
                .message("회원 삭제 성공")
                .results(List.of(memberService.delete(memberId, authentication)))
                .build();
    }


    /**
     * 회원 조회
     * @PathVariable : memberId
     */
    @GetMapping("/members/{memberId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse getMember(@PathVariable Long memberId) {
        return BaseResponse.builder()
                .message("회원 정보 조회 성공")
                .results(List.of(memberService.getMember(memberId)))
                .build();
    }

    /**
     * 회원 전체 조회
     */
    @GetMapping("/members")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse getMembers() {
        return BaseResponse.<MemberResponseDto>builder()
                .message("회원 전체 조회 성공")
                .results(memberService.getAllMembers())
                .build();
    }


}
