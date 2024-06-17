package goorm.spoco.domain.algorithm.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.algorithm.domain.Algorithm;

public record AlgorithmResponseDto(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long algorithmId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String algorithmTitle,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String content,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer submit,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer answer,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Double answerRate
) {
    public static AlgorithmResponseDto simple(Algorithm algorithm) {
        return new AlgorithmResponseDto(
                algorithm.getAlgorithmId(),
                algorithm.getTitle(),
                null,
                null,
                null,
                null
        );
    }

    public static AlgorithmResponseDto detail(Algorithm algorithm) {
        return new AlgorithmResponseDto(
                algorithm.getAlgorithmId(),
                algorithm.getTitle(),
                algorithm.getExplanation(),
                algorithm.getSubmit(),
                algorithm.getAnswer(),
                algorithm.getAnswerRate()
        );
    }
}
