package goorm.chat.dto.request;

import lombok.Getter;

public record MessageRequestDto(
        Long memberId,
        Long codeId,
        Long reviewId,
        Integer codeLine,
        String nickname,
        String content
) {

}
