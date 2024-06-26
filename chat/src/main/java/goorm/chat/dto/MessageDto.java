package goorm.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.chat.domain.Message;

import java.time.LocalDateTime;

public record MessageDto(
        String messageId,
        Long memberId,
        Long codeId,
        Long reviewId,
        String nickname,
        String content,
        LocalDateTime timestamp
) {
    public static MessageDto from(Message message) {
        return new MessageDto(
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
