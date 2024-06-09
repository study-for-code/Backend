package goorm.spoco.domain.message.controller.request;

import java.time.LocalDateTime;

public record MessageRequestDto(
        Long reviewId,
        Long memberId,
        String detail,
        LocalDateTime createAt
) {
}
