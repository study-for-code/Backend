package goorm.spoco.domain.review.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ReviewErrorCode reviewErrorCode;
    private final String message;

    public CustomException(ReviewErrorCode reviewErrorCode, String message) {
        this.reviewErrorCode = reviewErrorCode;
        this.message = message;
    }
}
