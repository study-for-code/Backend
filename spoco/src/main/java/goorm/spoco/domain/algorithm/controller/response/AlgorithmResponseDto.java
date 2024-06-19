package goorm.spoco.domain.algorithm.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;

import java.util.List;

public record AlgorithmResponseDto(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long algorithmId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String algorithmTitle,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String content,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<String> restrictions,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer memorySize,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer timeLimit,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer submit,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer answer,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Double answerRate,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<MemberResponseDto> solvedMembers
) {
    public static AlgorithmResponseDto simple(Algorithm algorithm) {
        return new AlgorithmResponseDto(
                algorithm.getAlgorithmId(),
                algorithm.getTitle(),
                null,
                null,
                null,
                null,
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
                algorithm.getRestrictions(),
                algorithm.getMemorySize(),
                algorithm.getTimeLimit(),
                algorithm.getSubmit(),
                algorithm.getAnswer(),
                algorithm.getAnswerRate(),
                null
        );
    }
}
