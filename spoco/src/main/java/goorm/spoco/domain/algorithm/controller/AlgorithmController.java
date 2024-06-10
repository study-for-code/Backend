package goorm.spoco.domain.algorithm.controller;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.dto.AlgorithmDTO;
import goorm.spoco.domain.algorithm.service.AlgorithmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/algorithms")
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    @PostMapping("/add")
    public ResponseEntity<AlgorithmDTO> addAlgorithm(@RequestBody AlgorithmDTO algorithmDTO) {
        Algorithm algorithm = new Algorithm();
        algorithm.setTitle(algorithm.getAlgorithmId() + "-" + algorithmDTO.getTitle());
        algorithm.setExplanation(algorithm.getExplanation());
        return ResponseEntity.status(HttpStatus.CREATED).body(algorithmDTO);
    }

}
