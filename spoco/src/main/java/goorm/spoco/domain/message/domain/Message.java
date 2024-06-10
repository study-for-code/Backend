package goorm.spoco.domain.message.domain;

import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.message.controller.request.MessageRequestDto;
import goorm.spoco.domain.review.domain.Review;
import goorm.spoco.domain.study.domain.Study;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private Long messageId;

    private String detail;

    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

    //== 연관관계 메서드 ==//
    public void addMember(Member member) {
        this.member = member;
        member.getMessages().add(this);
    }

    public void addReview(Review review) {
        this.review = review;
        review.getMessages().add(this);
    }

    //== 생성 메서드 ==//
    // 해당 매개변수는 request 객체로 변경
    public static Message message(Member member, Review review, MessageRequestDto messageRequestDto) {
        Message message = new Message();
        message.addMember(member);
        message.addReview(review);
        message.detail = messageRequestDto.detail();
        message.createAt = messageRequestDto.createAt();
        message.messageStatus = MessageStatus.OPEN;
        return message;
    }
}
