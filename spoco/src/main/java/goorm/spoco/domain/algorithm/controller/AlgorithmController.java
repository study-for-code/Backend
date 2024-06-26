package goorm.spoco.domain.algorithm.controller;

import goorm.spoco.domain.algorithm.controller.request.AlgorithmRequestDto;
import goorm.spoco.domain.algorithm.controller.request.AlgorithmSearchDto;
import goorm.spoco.domain.algorithm.controller.response.AlgorithmResponseDto;
import goorm.spoco.domain.algorithm.service.AlgorithmService;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    @PostMapping("/algorithms")
    public BaseResponse createAlgorithm(@RequestBody AlgorithmRequestDto algorithmRequestDto) {
        return BaseResponse.builder()
                .message("알고리즘 문제 생성")
                .results(List.of(algorithmService.create(algorithmRequestDto)))
                .build();
    }

    @DeleteMapping("/algorithms/{algorithmId}")
    public BaseResponse deleteAlgorithm(@PathVariable Long algorithmId) {
        algorithmService.delete(algorithmId);
        return BaseResponse.builder()
                .message("알고리즘 삭제")
                .build();
    }

    @PatchMapping("/algorithms/{algorithmId}")
    public BaseResponse updateAlgorithm(
            @PathVariable Long algorithmId,
            @RequestBody AlgorithmRequestDto algorithmRequestDto
    ) {
        return BaseResponse.builder()
                .message("알고리즘 업데이트")
                .results(List.of(algorithmService.update(algorithmId, algorithmRequestDto)))
                .build();
    }

    @GetMapping("/algorithms")
    public BaseResponse getAll() {
        return BaseResponse.<AlgorithmResponseDto>builder()
                .message("알고리즘 전체 조회")
                .results(algorithmService.getAll())
                .build();
    }

    @GetMapping("/algorithms/solved")
    public BaseResponse getAllWithSolvedMember(@RequestParam Long studyId) {
        return BaseResponse.<AlgorithmResponseDto>builder()
                .message("알고리즘 전체 조회 by 스터디 멤버")
                .results(algorithmService.getAllWithSolvedMember(studyId))
                .build();
    }

    @GetMapping("/algorithms/{algorithmId}")
    public BaseResponse getAlgorithm(@PathVariable Long algorithmId) {
        return BaseResponse.builder()
                .message("단일 알고리즘 검색")
                .results(List.of(algorithmService.getByAlgorithmId(algorithmId)))
                .build();
    }

    @GetMapping("/algorithms/search")
    public BaseResponse getAlgorithmsListByTitle(
            @RequestParam Long studyId,
            @RequestParam String title
    ) {
        return BaseResponse.<AlgorithmResponseDto>builder()
                .message("알고리즘 리스트 검색 by title")
                .results(algorithmService.searchAlgorithms(title, studyId))
                .build();
    }
}
