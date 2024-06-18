package goorm.spoco.infra.compiler.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record ResultDto(
    Integer testNum,

    // 유저 입력 코드
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String input,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String usedMemory,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String executionTime,

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
