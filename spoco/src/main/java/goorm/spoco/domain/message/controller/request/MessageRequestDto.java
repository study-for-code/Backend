package goorm.spoco.domain.message.controller.request;

import goorm.spoco.domain.message.controller.response.MessageResponseDto;

import java.time.LocalDateTime;

public record MessageRequestDto(
        Long memberId,
        Long codeId,
        Long reviewId,
        String nickname,
        String content
) {
}
