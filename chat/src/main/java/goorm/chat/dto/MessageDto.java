package goorm.chat.dto;

import goorm.chat.domain.Message;

import java.lang.reflect.Member;

public record MessageDto(
        Long memberId,
        Long codeId,
        Long reviewId,
        Integer codeLine,
        String nickname,
        String content
) {
    public static MessageDto from(Message message) {
        return new MessageDto(
                message.getMemberId(),
                message.getCodeId(),
                message.getReviewId(),
                message.getCodeLine(),
                message.getNickname(),
                message.getContent()
        );
    }
}
