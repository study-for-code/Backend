package goorm.spoco.domain.testcase.controller.response;

import goorm.spoco.domain.testcase.domain.Testcase;

public record TestcaseResponseDto(
        Long testcaseId,
        String input,
        String output
) {
    public static TestcaseResponseDto from(Testcase testcase) {
        return new TestcaseResponseDto(
                testcase.getTestcaseId(),
                testcase.getInput(),
                testcase.getOutput()
        );
    }
}
