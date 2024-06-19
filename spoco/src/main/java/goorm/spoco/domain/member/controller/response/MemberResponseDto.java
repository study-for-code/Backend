package goorm.spoco.domain.member.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.auth.controller.request.MemberSignInDto;
import goorm.spoco.domain.code.controller.response.CodeResponseDto;
import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.member.domain.Member;

public record MemberResponseDto(
        Long memberId,
        String email,
        String nickname,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String token,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        CodeResponseDto code
) {
    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                null,
                null
        );
    }

    public static MemberResponseDto from(Join join) {
        return new MemberResponseDto(
                join.getMember().getMemberId(),
                join.getMember().getEmail(),
                join.getMember().getNickname(),
                null,
                CodeResponseDto.load(join.getMember().getCodes().get(0))
        );
    }

    public static MemberResponseDto signIn(Member member, String token) {
        return new MemberResponseDto(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                token,
                null
        );
    }
}
