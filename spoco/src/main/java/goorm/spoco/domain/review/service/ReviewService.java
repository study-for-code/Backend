package goorm.spoco.domain.review.service;

import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.code.repository.CodeRepository;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.domain.message.domain.Message;
import goorm.spoco.domain.message.repository.MessageRepository;
import goorm.spoco.domain.review.controller.response.ReviewResponseDto;
import goorm.spoco.domain.review.domain.Review;
import goorm.spoco.domain.review.domain.ReviewStatus;
import goorm.spoco.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CodeRepository codeRepository;

    @Transactional
    public ReviewResponseDto createReview(Long codeId, Integer codeLine) {

        /*Code code = codeRepository.findByCodeId(codeId)
                .orElseThrow(() -> new CodeNotFoundException("해당하는 코드가 없습니다. = " + codeId));

        Review review = Review.review(code, codeLine);
        reviewRepository.save(review);*/

        return new ReviewResponseDto(1, ReviewStatus.OPEN.name());
    }

    @Transactional
    public ReviewResponseDto deleteReview(Long reviewId) {
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("해당 리뷰가 존재하지 않습니다." + reviewId))
                .delete();

        return new ReviewResponseDto(review.getCodeLine(), review.getReviewStatus().name());
    }
}
