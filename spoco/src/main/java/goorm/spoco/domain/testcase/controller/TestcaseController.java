package goorm.spoco.domain.testcase.controller;

import goorm.spoco.domain.testcase.domain.Testcase;
import goorm.spoco.domain.testcase.dto.TestcaseDTO;
import goorm.spoco.domain.testcase.service.TestcaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TestcaseController {

    private final TestcaseService testcaseService;

    @PostMapping("/api/addTestcase")
    public ResponseEntity<TestcaseDTO> addTestcase(
            @RequestBody Testcase testcase,
            @RequestParam Long algorithmId
    ) {
        Testcase saveedTestcase = testcaseService.save(testcase, algorithmId);
        TestcaseDTO testcaseDTO = new TestcaseDTO(testcase.getInput(), testcase.getOutput());
        return ResponseEntity.status(HttpStatus.OK).body(testcaseDTO);
    }

    @DeleteMapping("/api/delete/{id}")
    public ResponseEntity<Testcase> deleteTestcase(@PathVariable Long id) {
//        Testcase deleted = testcaseService.delete(id);
        testcaseService.delete(id);
//        return ResponseEntity.status(HttpStatus.OK).body(deleted);
        return ResponseEntity.noContent().build();
    }


}
