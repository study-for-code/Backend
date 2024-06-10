package goorm.spoco.domain.review.controller;

import goorm.spoco.domain.review.controller.response.ReviewResponseDto;
import goorm.spoco.domain.review.service.ReviewService;
import goorm.spoco.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review/{codeId}")
    public BaseResponse create(
            @PathVariable Long codeId,
            @RequestParam Integer codeLine
    ) {
        ReviewResponseDto review = reviewService.createReview(codeId, codeLine);
        return BaseResponse.builder()
                .message("리뷰 생성 성공")
                .results(List.of(review))
                .build();
    }

    @DeleteMapping("/review/{reviewId}")
    public BaseResponse delete(
            @PathVariable Long reviewId,
            @RequestParam Long memberId
    ) {
        ReviewResponseDto review = reviewService.deleteReview(reviewId, memberId);
        return BaseResponse.builder()
                .message("리뷰 삭제 성공")
                .results(List.of(review))
                .build();
    }

    @GetMapping("/review/{codeId}")
    public BaseResponse reviewList(@PathVariable Long codeId) {
        return BaseResponse.<ReviewResponseDto>builder()
                .message("리뷰 리스트 조회 성공")
                .results(reviewService.getAllByCodeId(codeId))
                .build();
    }
}
