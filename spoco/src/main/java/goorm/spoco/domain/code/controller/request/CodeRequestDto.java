package goorm.spoco.domain.code.controller.request;

public record CodeRequestDto(
        Long codeId,
        String language,
        String detail
) {
}