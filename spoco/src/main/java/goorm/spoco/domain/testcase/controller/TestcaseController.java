package goorm.spoco.domain.testcase.controller;

import goorm.spoco.domain.testcase.domain.Testcase;
import goorm.spoco.domain.testcase.domain.TestcaseStatus;
import goorm.spoco.domain.testcase.dto.TestcaseDTO;
import goorm.spoco.domain.testcase.service.TestcaseService;
import goorm.spoco.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/testcases")
public class TestcaseController {

    private final TestcaseService testcaseService;

    @PostMapping("/{algorithmId}/create")
    public BaseResponse createTestcase(
            @RequestBody Testcase testcase,
            @PathVariable Long algorithmId
    ) {
        testcaseService.save(testcase, algorithmId);
        TestcaseDTO testcaseDTO = new TestcaseDTO(testcase.getInput(), testcase.getOutput());
        return BaseResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("테스트 케이스 생성")
                .results(List.of(testcaseDTO))
                .build();
    }

    @DeleteMapping("/{id}/delete")
    public BaseResponse deleteTestcase(@PathVariable Long id) {

        testcaseService.delete(id);
        return BaseResponse.builder()
                .message("테스트 케이스 삭제")
                .build();
    }

    @PatchMapping("/{id}/update")
    public BaseResponse updateTestcase(
            @PathVariable("id") Long testcaseId,
            @RequestBody TestcaseDTO testcaseDTO
    ) {
        testcaseService.update(testcaseId, testcaseDTO);
        return BaseResponse.builder()
                .message("테스트케이스 업데이트")
                .results(List.of(testcaseDTO))
                .build();
    }
}
