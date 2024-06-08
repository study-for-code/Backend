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
import goorm.spoco.domain.review.exception.CustomException;
import goorm.spoco.domain.review.exception.ReviewErrorCode;
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

        Code code = codeRepository.findByCodeId(codeId)
                .orElseThrow(() -> new CustomException(ReviewErrorCode.RESOURCE_NOT_FOUND, "해당 코드(" + codeId + ")가 존재하지 않습니다."));

        Review review = Review.review(code, codeLine);
        reviewRepository.save(review);

        return new ReviewResponseDto(review.getCodeLine(), review.getReviewStatus().name());
    }

    @Transactional
    public ReviewResponseDto deleteReview(Long reviewId) {
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new CustomException(ReviewErrorCode.RESOURCE_NOT_FOUND, "해당 리뷰(" + reviewId + ")가 존재하지 않습니다."));

        review.delete();

        return new ReviewResponseDto(review.getCodeLine(), review.getReviewStatus().name());
    }

    public List<ReviewResponseDto> getAllByCodeId(Long codeId) {
        return reviewRepository.findAllByCodeIdAndReviewStatus(codeId, ReviewStatus.OPEN)
                .stream().map(review -> new ReviewResponseDto(review.getCodeLine(), null))
                .collect(Collectors.toList());
    }
}
