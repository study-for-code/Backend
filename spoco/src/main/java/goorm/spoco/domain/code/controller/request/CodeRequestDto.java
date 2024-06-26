package goorm.spoco.domain.code.controller.request;

public record CodeRequestDto(
        Long algorithmId,
        String language,
        String detail
) {
}
