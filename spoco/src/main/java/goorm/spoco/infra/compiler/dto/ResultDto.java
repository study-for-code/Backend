package goorm.spoco.infra.compiler.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
public record ResultDto(
    Integer testNum,
    // 유저 입력 코드
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String input,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Double usedMemory,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Double executionTime,
    // 정답 출력
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String expectedResult,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String errorMessage,
    // 실제 출력
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String actualResult,
    // 결과 상태
    ResultStatus status // PASS, FAIL, ERROR

) {
}
