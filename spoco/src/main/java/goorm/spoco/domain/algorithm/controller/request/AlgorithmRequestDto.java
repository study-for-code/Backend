package goorm.spoco.domain.algorithm.controller.request;

import java.util.List;

public record AlgorithmRequestDto(
        String title,
        String explanation,
        List<String> restrictions,
        Integer memorySize,
        Integer timeLimit
) {
}
