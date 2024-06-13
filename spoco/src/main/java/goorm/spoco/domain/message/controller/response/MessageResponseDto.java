package goorm.spoco.domain.message.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.message.controller.request.MessageRequestDto;
import goorm.spoco.domain.message.domain.Message;
import goorm.spoco.domain.message.domain.MessageStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public record MessageResponseDto(
        String messageId,
        Long memberId,
        Long codeId,
        Long reviewId,
        String nickname,
        String content,
        LocalDateTime timestamp
) {
        public static MessageResponseDto from(Message message) {
                return new MessageResponseDto(
                        message.getId(),
                        message.getMemberId(),
                        message.getCodeId(),
                        message.getReviewId(),
                        message.getNickname(),
                        message.getContent(),
                        message.getCreateAt()
                );
        }
}
