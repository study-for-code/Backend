package goorm.spoco.domain.review.controller;

import goorm.spoco.domain.review.controller.response.ReviewResponse;
import goorm.spoco.domain.review.controller.response.ReviewResponseDto;
import goorm.spoco.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review/{codeId}")
    public ReviewResponse create(
            @PathVariable Long codeId,
            @RequestParam Integer codeLine
    ) {
        ReviewResponseDto review = reviewService.createReview(codeId, codeLine);
        return ReviewResponse.builder()
                .code(HttpStatus.OK.value())
                .message("리뷰 생성 성공")
                .results(List.of(review))
                .build();
    }

    @DeleteMapping("/review/{reviewId}")
    public ReviewResponse delete(@PathVariable Long reviewId) {
        ReviewResponseDto review = reviewService.deleteReview(reviewId);
        return ReviewResponse.builder()
                .code(HttpStatus.OK.value())
                .message("리뷰 삭제 성공")
                .results(List.of(review))
                .build();
    }
}
