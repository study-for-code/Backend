package goorm.spoco.domain.review.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode {
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, 404),
    DUPLICATE_OBJECT(HttpStatus.CONFLICT, 409);

    private final HttpStatus httpStatus;
    private final Integer code;
}
