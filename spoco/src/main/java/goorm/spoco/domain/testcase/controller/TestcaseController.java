package goorm.spoco.domain.testcase.controller;

import goorm.spoco.domain.testcase.controller.request.TestcaseRequestDto;
import goorm.spoco.domain.testcase.controller.response.TestcaseResponseDto;
import goorm.spoco.domain.testcase.domain.Testcase;
import goorm.spoco.domain.testcase.dto.TestcaseDTO;
import goorm.spoco.domain.testcase.service.TestcaseService;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestcaseController {

    private final TestcaseService testcaseService;

    @PostMapping("/testcases/{algorithmId}")
    public BaseResponse createTestcase(
            @RequestBody TestcaseRequestDto testcaseRequestDto,
            @PathVariable Long algorithmId
    ) {
        return BaseResponse.builder()
                .message("테스트 케이스 생성")
                .results(List.of(testcaseService.create(testcaseRequestDto, algorithmId)))
                .build();
    }

    @DeleteMapping("/testcases/{testcaseId}")
    public BaseResponse deleteTestcase(@PathVariable Long testcaseId) {
        testcaseService.delete(testcaseId);
        return BaseResponse.builder()
                .message("테스트 케이스 삭제")
                .build();
    }

    @PatchMapping("/testcases/{testcaseId}")
    public BaseResponse updateTestcase(
            @PathVariable Long testcaseId,
            @RequestBody TestcaseRequestDto testcaseRequestDto
    ) {
        return BaseResponse.builder()
                .message("테스트케이스 업데이트")
                .results(List.of(testcaseService.update(testcaseId, testcaseRequestDto)))
                .build();
    }

    @GetMapping("/testcases/{algorithmId}")
    public BaseResponse getAllByAlgorithmId(
            @PathVariable Long algorithmId
    ) {
        return BaseResponse.<TestcaseResponseDto>builder()
                .message("해당 알고리즘 테스트케이스 반환")
                .results(testcaseService.getAllByAlgorithmId(algorithmId))
                .build();
    }
}
