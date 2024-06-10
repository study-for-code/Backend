package goorm.spoco.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

import static lombok.Builder.*;

@Getter
@Builder
public class BaseResponse<T> {


    @Default
    private Integer code = ErrorCode.OK.getCode();
    @Default
    private HttpStatus httpStatus = HttpStatus.OK;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> results;
}
