package goorm.spoco.domain.member.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.spoco.domain.member.domain.Member;

public record MemberResponseDto(
        @JsonIgnore
        Long memberId,
        @JsonIgnore
        String email,
        @JsonIgnore
        String nickname
) {
    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getEmail(),
                member.getNickname()
        );
    }
}
