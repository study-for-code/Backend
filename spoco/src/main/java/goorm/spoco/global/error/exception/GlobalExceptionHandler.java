package goorm.spoco.global.error.exception;

import goorm.spoco.domain.review.controller.response.ReviewResponse;
import goorm.spoco.domain.review.exception.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ReviewResponse customExceptionHandler(CustomException e) {
        return ReviewResponse.builder()
                .code(e.getReviewErrorCode().getCode())
                .httpStatus(e.getReviewErrorCode().getHttpStatus())
                .message(e.getMessage())
                .build();
    }
}
