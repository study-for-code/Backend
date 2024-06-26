package goorm.spoco.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, 401),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, 404),
    DUPLICATE_OBJECT(HttpStatus.CONFLICT, 409),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 402),
    LIMIT_CATEGORY(HttpStatus.BAD_REQUEST, 400),
    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500),
    COMPILE_ERROR(HttpStatus.BAD_REQUEST, 400),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400),

    OK(HttpStatus.OK, 200);



    private final HttpStatus httpStatus;
    private final Integer code;
}
