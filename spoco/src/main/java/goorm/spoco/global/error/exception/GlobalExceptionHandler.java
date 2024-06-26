package goorm.spoco.global.error.exception;

import goorm.spoco.global.common.response.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public BaseResponse customExceptionHandler(CustomException e) {
        return BaseResponse.builder()
                .code(e.getErrorCode().getCode())
                .httpStatus(e.getErrorCode().getHttpStatus())
                .message(e.getMessage())
                .build();
    }
}
