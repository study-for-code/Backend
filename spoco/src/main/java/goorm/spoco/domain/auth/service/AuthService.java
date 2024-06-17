package goorm.spoco.domain.auth.service;

import goorm.spoco.domain.auth.controller.request.MemberSignInDto;
import goorm.spoco.domain.auth.controller.response.MemberInfoDto;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.global.common.auth.SpocoUserDetails;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import goorm.spoco.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public MemberResponseDto login(MemberSignInDto memberSignInDto) {
        Member member = memberRepository.findByEmail(memberSignInDto.email())
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 회원입니다."));

        if (!encoder.matches(memberSignInDto.password(), member.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH, "비밀번호가 일치하지 않습니다.");
        }

        MemberInfoDto memberInfoDto = MemberInfoDto.from(member);
        String token = jwtUtil.createAccessToken(memberInfoDto);
        return MemberResponseDto.signIn(member, token);
    }

    public MemberResponseDto getMyInfo(Authentication authentication) {
        SpocoUserDetails userDetails = (SpocoUserDetails) authentication.getDetails();
        log.info("details = {}", authentication);
        Member member = memberRepository.findByMemberId(Long.parseLong(userDetails.getUsername()))
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 회원입니다."));
        return MemberResponseDto.from(member);
    }
}
