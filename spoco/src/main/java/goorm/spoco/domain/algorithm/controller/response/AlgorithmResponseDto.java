package goorm.spoco.domain.algorithm.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record AlgorithmResponseDto(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long algorithmId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String algorithmTitle,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String explanation,
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
        List<MemberResponseDto> solvedMembers,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String SubscribeStatus
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
                null,
                null
        );
    }

    public static AlgorithmResponseDto all(Algorithm algorithm, List<MemberResponseDto> solvedMembers, boolean check) {
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
                solvedMembers,
                check ? "SUBSCRIBE" : "NONE"
        );
    }
}
