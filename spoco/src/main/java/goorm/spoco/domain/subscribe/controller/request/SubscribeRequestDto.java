package goorm.spoco.domain.subscribe.controller.request;

public record SubscribeRequestDto(
    Long categoryId,
    Long algorithmId
) {
}
