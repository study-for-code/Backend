package goorm.spoco.domain.message.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.message.domain.MessageStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public record MessageResponseDto(
        Long messageId,
        String detail,
        LocalDateTime createAt,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String messageStatus
) {
}
