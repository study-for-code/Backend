package goorm.spoco.domain.algorithm.controller;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.dto.AlgorithmDTO;
import goorm.spoco.domain.algorithm.service.AlgorithmService;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/algorithm")
@Slf4j
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    @PostMapping("/create")
    public BaseResponse createAlgorithm(@RequestBody AlgorithmDTO algorithmDTO) {
        Algorithm algorithm = new Algorithm(algorithmDTO.getTitle(), algorithmDTO.getExplanation());
        algorithmService.save(algorithm);
        // == num + title 으로 바꾸는 경우에는 ID의 생성원칙도 바꿔야 하므로 추후 논의가 필요하다... == //
        algorithm.setTitle(algorithm.getAlgorithmId() + "-" + algorithm.getTitle());
        algorithmService.save(algorithm);
        return BaseResponse.builder()
                .message("알고리즘 문제 생성")
                .httpStatus(HttpStatus.CREATED)
                .results(List.of(algorithm))
                .build();
    }

    @DeleteMapping("/{id}/delete")
    public BaseResponse deleteAlgorithm(@PathVariable Long id) {
        algorithmService.delete(id);
        return BaseResponse.builder()
                .message("알고리즘 삭제")
                .build();
    }

    @PatchMapping("/{id}/update")
    public BaseResponse updateAlgorithm(
            @PathVariable Long id,
            @RequestBody AlgorithmDTO algorithmDTO
    ) {
        algorithmService.update(id, algorithmDTO);
        return BaseResponse.builder()
                .message("알고리즘 업데이트")
                .results(List.of(algorithmDTO))
                .build();
    }

    @GetMapping("/searchAlgorithms")
    public BaseResponse getAlgorithmsListByTitle(@RequestParam String title) {
        List<Algorithm> algorithms = algorithmService.searchAlgorithms(title);
        return BaseResponse.<Algorithm>builder()
                .message("알고리즘 리스트 검색 by title")
                .results(algorithms)
                .build();
    }

    @GetMapping("/{id}/getAlgorithm")
    public BaseResponse getAlgorithm(@PathVariable Long id) {
        Algorithm algorithm = algorithmService.get(id);
        return BaseResponse.builder()
                .message("알고리즘 검색")
                .results(List.of(algorithm))
                .build();
    }
}
