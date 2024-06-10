package goorm.chat.domain;

import goorm.chat.dto.request.MessageRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "messages")
public class Message {

    @Id
    private String id;
    private Long memberId;
    private Long codeId;
    private Long reviewId;
    private Integer codeLine;
    private String nickname;
    private String content;
    private LocalDateTime createAt;
    private MessageStatus messageStatus;

    public static Message create(MessageRequestDto messageRequestDto) {
        Message message = new Message();
        message.memberId = messageRequestDto.memberId();
        message.codeId = messageRequestDto.codeId();
        message.reviewId = messageRequestDto.reviewId();
        message.codeLine = messageRequestDto.codeLine();
        message.nickname = messageRequestDto.nickname();
        message.content = messageRequestDto.content();
        message.createAt = LocalDateTime.now();
        message.messageStatus = MessageStatus.ACTIVE;
        return message;
    }
}
