package goorm.spoco.domain.auth.controller;

import goorm.spoco.domain.auth.service.AuthService;
import goorm.spoco.domain.auth.controller.request.MemberSignInDto;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public BaseResponse login(@RequestBody MemberSignInDto memberSignInDto) {
        return BaseResponse.builder()
                .message("로그인 성공")
                .results(List.of(authService.login(memberSignInDto)))
                .build();
    }

    /**
     * 본인 조회
     * @PathVariable : memberId
     */
    @GetMapping("/me")
    public BaseResponse findMyInfo(Authentication authentication) {
        return BaseResponse.builder()
                .message("본인 정보 조회 성공")
                .results(List.of(authService.getMyInfo(authentication)))
                .build();
    }
}
