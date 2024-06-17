package goorm.spoco.domain.subscribe.controller.response;

import goorm.spoco.domain.algorithm.controller.response.AlgorithmResponseDto;
import goorm.spoco.domain.subscribe.domain.Subscribe;

public record SubscribeResponseDto(
        Long subscribeId,
        AlgorithmResponseDto algorithm
) {
    public static SubscribeResponseDto from(Subscribe subscribe) {
        return new SubscribeResponseDto(
                subscribe.getSubscribeId(),
                AlgorithmResponseDto.simple(subscribe.getAlgorithm())
        );
    }
}
