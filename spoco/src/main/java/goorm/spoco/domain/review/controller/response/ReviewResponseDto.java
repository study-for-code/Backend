package goorm.spoco.domain.review.controller.response;

import goorm.spoco.domain.review.domain.Review;

public record ReviewResponseDto(

        Long reviewId,
        Integer codeLine
) {

        public static ReviewResponseDto from(Review review) {
                return new ReviewResponseDto(
                        review.getReviewId(),
                        review.getCodeLine()
                );
        }
}
