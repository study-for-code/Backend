package goorm.spoco.domain.algorithm.controller;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.dto.AlgorithmDTO;
import goorm.spoco.domain.algorithm.service.AlgorithmService;
import goorm.spoco.global.common.BaseResponse;
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

}
