package goorm.spoco.domain.member.controller.request;

public record MemberModifyDto(
        String nickname,
        String password
) {
}
