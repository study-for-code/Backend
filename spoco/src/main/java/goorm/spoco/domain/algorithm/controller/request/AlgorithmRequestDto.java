package goorm.spoco.domain.algorithm.controller.request;

import jakarta.persistence.Lob;
import lombok.Builder;

import java.util.List;

public record AlgorithmRequestDto(
        String title,
        String explanation,
        List<String> restrictions,
        Integer memorySize,
        Integer timeLimit
) {
        public AlgorithmRequestDto {
                if (memorySize == null) {
                        memorySize = 512;
                }
                if (timeLimit == null) {
                        timeLimit = 2;
                }
        }
}
