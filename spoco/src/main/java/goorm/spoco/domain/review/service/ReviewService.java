package goorm.spoco.domain.review.service;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.code.domain.CodeStatus;
import goorm.spoco.domain.code.repository.CodeRepository;
import goorm.spoco.domain.member.domain.Grade;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.domain.review.controller.response.ReviewResponseDto;
import goorm.spoco.domain.review.domain.Review;
import goorm.spoco.domain.review.domain.ReviewStatus;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.domain.review.exception.ReviewErrorCode;
import goorm.spoco.domain.review.repository.ReviewRepository;
import goorm.spoco.global.error.exception.ErrorCode;
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

    private final MemberRepository memberRepository;

    @Transactional
    public ReviewResponseDto createReview(Long codeId, Integer codeLine) {
        Code code = codeRepository.findByCodeId(codeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 코드(" + codeId + ")가 존재하지 않습니다."));

        Review review = Review.review(code, codeLine);
        reviewRepository.save(review);

        return new ReviewResponseDto(review.getReviewId(), review.getCodeLine(), review.getReviewStatus().name());
    }

    @Transactional
    public ReviewResponseDto deleteReview(Long reviewId, Long memberId) {
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 리뷰(" + reviewId + ")가 존재하지 않습니다."));

        //== 추후 스프링 시큐리티로 처리
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.DUPLICATE_OBJECT, "해당 멤버(" + memberId + ")가 존재하지 않습니다."));


        if (review.getCode().getMember().equals(member) || member.getGrade().equals(Grade.ADMIN)) {
            review.delete();
        } else {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "코드 작성자가 아닙니다.");
        }
        //==

        return new ReviewResponseDto(review.getReviewId(), review.getCodeLine(), review.getReviewStatus().name());
    }

    public List<ReviewResponseDto> getAllByCodeId(Long codeId) {
        return reviewRepository.findAllByCode_CodeIdAndReviewStatus(codeId, ReviewStatus.OPEN)
                .stream().map(review -> new ReviewResponseDto(review.getReviewId(), review.getCodeLine(), null))
                .collect(Collectors.toList());
    }
}
