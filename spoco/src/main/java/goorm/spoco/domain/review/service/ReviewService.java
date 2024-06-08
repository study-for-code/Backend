package goorm.spoco.domain.review.service;

import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.code.repository.CodeRepository;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.domain.message.domain.Message;
import goorm.spoco.domain.message.repository.MessageRepository;
import goorm.spoco.domain.review.domain.Review;
import goorm.spoco.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CodeRepository codeRepository;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createReview(Long memberId, Long codeId, Integer codeLine) {

        Code code = codeRepository.findByCodeId(codeId)
                .orElseThrow(() -> new RuntimeException("해당하는 코드가 없습니다. = " + codeId));

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("해당하는 멤버가 없습니다. = " + memberId));

        Review review = Review.review(code, codeLine);
        reviewRepository.save(review);

        return review.getReviewId();
    }
}
