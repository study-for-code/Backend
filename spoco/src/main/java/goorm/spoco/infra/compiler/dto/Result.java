package goorm.spoco.infra.compiler.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {
    private Integer testNum;
    private String input;
    private String expectedResult;
    private String actualResult;
    private ResultStatus status; // PASS, FAIL, ERROR

    public Result() {
    }

    public Result(Integer testNum, String input, String expectedResult, String actualResult, ResultStatus status) {
        this.testNum = testNum;
        this.input = input;
        this.expectedResult = expectedResult;
        this.actualResult = actualResult;
        this.status = status;
    }
}
