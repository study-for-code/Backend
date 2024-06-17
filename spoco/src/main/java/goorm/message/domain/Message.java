package goorm.message.domain;

import goorm.message.controller.request.MessageRequestDto;
import goorm.spoco.global.common.response.Status;
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

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "REVIEW_ID")
//    private Review review;
//
//    //== 연관관계 메서드 ==//
//    public void addMember(Member member) {
//        this.member = member;
//        member.getMessages().add(this);
//    }
//
//    public void addReview(Review review) {
//        this.review = review;
//        review.getMessages().add(this);
//    }

    //== 생성 메서드 ==//
    // 해당 매개변수는 request 객체로 변경
    public static Message create(MessageRequestDto messageRequestDto) {
        Message message = new Message();
        message.memberId = messageRequestDto.memberId();
        message.codeId = messageRequestDto.codeId();
        message.reviewId = messageRequestDto.reviewId();
        message.nickname = messageRequestDto.nickname();
        message.content = messageRequestDto.content();
        message.createAt = LocalDateTime.now();
        message.status = Status.ACTIVE;
        return message;
    }
}
