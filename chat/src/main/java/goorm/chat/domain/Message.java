package goorm.chat.domain;

import goorm.chat.dto.MessageDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "messages")
public class Message {

    @Id
    private String id;
    private Long memberId;
    private Long codeId;
    private Long reviewId;
    private String nickname;
    private String content;
    private LocalDateTime createAt;
    private MessageStatus messageStatus;

    public static Message create(MessageDto messageDto) {
        Message message = new Message();
        message.memberId = messageDto.memberId();
        message.codeId = messageDto.codeId();
        message.reviewId = messageDto.reviewId();
        message.nickname = messageDto.nickname();
        message.content = messageDto.content();
        message.createAt = LocalDateTime.now();
        message.messageStatus = MessageStatus.ACTIVE;
        return message;
    }
}
