package goorm.spoco.domain.subscribe.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.algorithm.controller.response.AlgorithmResponseDto;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.subscribe.domain.Subscribe;

import java.util.List;

public record SubscribeResponseDto(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long subscribeId,
        AlgorithmResponseDto algorithm,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<MemberResponseDto> submitMembers
) {
    public static SubscribeResponseDto simple(Subscribe subscribe) {
        return new SubscribeResponseDto(
                subscribe.getSubscribeId(),
                AlgorithmResponseDto.simple(subscribe.getAlgorithm()),
                null
        );
    }

    public static SubscribeResponseDto from(Subscribe subscribe, List<MemberResponseDto> submitMembers) {
        return new SubscribeResponseDto(
                subscribe.getSubscribeId(),
                AlgorithmResponseDto.detail(subscribe.getAlgorithm()),
                submitMembers
        );
    }
}
