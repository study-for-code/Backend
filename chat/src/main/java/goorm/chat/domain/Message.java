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
    private Status status;

    public static Message create(MessageDto messageDto) {
        Message message = new Message();
        message.memberId = messageDto.memberId();
        message.codeId = messageDto.codeId();
        message.reviewId = messageDto.reviewId();
        message.nickname = messageDto.nickname();

        // 테스트 용 if
        message.content = messageDto.content();
        if (messageDto.timestamp() == null) {
            message.createAt = LocalDateTime.now();
        } else {
            message.createAt = messageDto.timestamp();
        }

        message.status = Status.ACTIVE;
        return message;
    }
}
