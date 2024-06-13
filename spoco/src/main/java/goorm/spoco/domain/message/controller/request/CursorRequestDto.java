package goorm.spoco.domain.message.controller.request;

import java.time.LocalDateTime;

public record CursorRequestDto(
        Long reviewId,
        LocalDateTime timestamp,
        String lastId
) {
}
