package goorm.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.chat.domain.Message;

import java.time.LocalDateTime;

public record MessageDto(
        Long memberId,
        Long codeId,
        Long reviewId,
        String nickname,
        String content,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime timestamp
) {
    public static MessageDto from(Message message) {
        return new MessageDto(
                message.getMemberId(),
                message.getCodeId(),
                message.getReviewId(),
                message.getNickname(),
                message.getContent(),
                message.getCreateAt()
        );
    }
}
