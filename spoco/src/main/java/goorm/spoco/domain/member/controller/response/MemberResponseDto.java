package goorm.spoco.domain.member.controller.response;

import goorm.spoco.domain.member.domain.Member;

public record MemberResponseDto(
        Long memberId,
        String email,
        String nickname
) {
    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname()
        );
    }
}
