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

    // 실제 출력
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String actualResult,

    // 결과 상태
    ResultStatus status // PASS, FAIL, ERROR

) {
    public static ResultDto testing(ResultDto result) {
        return new ResultDto(
                result.testNum(),
                result.input(),
                null,
                null,
                result.expectedResult(),
                result.actualResult(),
                result.status()
        );
    }

    public static ResultDto submit(ResultDto result) {
        return new ResultDto(
                result.testNum(),
                null,
                result.usedMemory(),
                result.executionTime(),
                null,
                null,
                result.status()
        );
    }
//
//    public Result(Integer testNum, String input, String expectedResult, String actualResult, ResultStatus status) {
//        this.testNum = testNum;
//        this.input = input;
//        this.expectedResult = expectedResult;
//        this.actualResult = actualResult;
//        this.status = status;
//    }
//
//    public Result(String actualResult, ResultStatus status) {
//        this.actualResult = actualResult;
//        this.status = status;
//    }
}
