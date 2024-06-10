package goorm.spoco.domain.message.service;

import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.domain.message.controller.request.MessageRequestDto;
import goorm.spoco.domain.message.controller.response.MessageResponseDto;
import goorm.spoco.domain.message.domain.Message;
import goorm.spoco.domain.message.repository.MessageRepository;
import goorm.spoco.domain.review.domain.Review;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.domain.review.repository.ReviewRepository;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public MessageResponseDto createMessage(MessageRequestDto messageRequestDto) {
        Member member = memberRepository.findByMemberId(messageRequestDto.memberId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 멤버(" + messageRequestDto.memberId() + ")가 존재하지 않습니다."));

        Review review = reviewRepository.findByReviewId(messageRequestDto.reviewId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 리뷰(" + messageRequestDto.reviewId() + ")가 존재하지 않습니다."));

        Message message = Message.message(member, review, messageRequestDto);
        messageRepository.save(message);

        return new MessageResponseDto(message.getMessageId(), message.getDetail(), message.getCreateAt(), message.getMessageStatus().name());
    }
}
