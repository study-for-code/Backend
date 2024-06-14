package goorm.spoco.domain.member.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.spoco.domain.member.domain.Member;

public record MemberResponseDto(
        @JsonIgnore
        Long memberId,
        @JsonIgnore
        String email,
        @JsonIgnore
        String nickname,
        @JsonIgnore
        String password
) {
    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                member.getPassword()
        );
    }
}
