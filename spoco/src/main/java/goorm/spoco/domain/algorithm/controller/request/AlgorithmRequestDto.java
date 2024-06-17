package goorm.spoco.domain.algorithm.controller.request;

public record AlgorithmRequestDto(
        String title,
        String explanation,
        String restrictions,
        Integer timeLimit
) {
}
