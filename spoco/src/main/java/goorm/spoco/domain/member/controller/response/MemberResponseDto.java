package goorm.spoco.domain.member.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.auth.controller.request.MemberSignInDto;
import goorm.spoco.domain.member.domain.Member;

public record MemberResponseDto(
        Long memberId,
        String email,
        String nickname,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String token
) {
    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                null
        );
    }

    public static MemberResponseDto signIn(Member member, String token) {
        return new MemberResponseDto(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                token
        );
    }
}
