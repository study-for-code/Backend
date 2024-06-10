package goorm.spoco.domain.algorithm.controller;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.dto.AlgorithmDTO;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.algorithm.service.AlgorithmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/algorithm")
@Slf4j
public class AlgorithmController {

    private final AlgorithmService algorithmService;
    private final AlgorithmRepository algorithmRepository;

    @PostMapping("/create")
    public ResponseEntity<AlgorithmDTO> createAlgorithm(@RequestBody AlgorithmDTO algorithmDTO) {
        Algorithm algorithm = new Algorithm(algorithmDTO.getTitle(), algorithmDTO.getExplanation());
        algorithmService.save(algorithm);
        // 문제에 고유번호 부여
        algorithm.setTitle(algorithm.getAlgorithmId() + "-" + algorithm.getTitle());
        algorithmService.save(algorithm);
        return ResponseEntity.status(HttpStatus.CREATED).body(algorithmDTO);
    }

}
