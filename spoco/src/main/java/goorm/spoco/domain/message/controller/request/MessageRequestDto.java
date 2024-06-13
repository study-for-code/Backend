package goorm.spoco.domain.message.controller.request;

public record MessageRequestDto(
        Long memberId,
        Long codeId,
        Long reviewId,
        String nickname,
        String content
) {
}
