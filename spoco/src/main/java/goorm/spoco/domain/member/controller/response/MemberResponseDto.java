package goorm.spoco.domain.member.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.auth.controller.request.MemberSignInDto;
import goorm.spoco.domain.code.controller.response.CodeResponseDto;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.infra.compiler.dto.ResultStatus;

import java.util.List;

public record MemberResponseDto(
        Long memberId,
        String email,
        String nickname,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String token,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long codeId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String submitStatus
) {
    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                null,
                null,
                null
        );
    }

    public static MemberResponseDto review(Join join, Code code) {
        return new MemberResponseDto(
                join.getMember().getMemberId(),
                join.getMember().getEmail(),
                join.getMember().getNickname(),
                null,
                code == null ? null : code.getCodeId().equals(ResultStatus.PASS) ? code.getCodeId() : null,
                code == null ? "UNFINISHED" : code.getAnswerType().equals(ResultStatus.PASS) ? "FINISHED" : "UNFINISHED"
        );
    }

    public static MemberResponseDto signIn(Member member, String token) {
        return new MemberResponseDto(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                token,
                null,
                null
        );
    }
}
