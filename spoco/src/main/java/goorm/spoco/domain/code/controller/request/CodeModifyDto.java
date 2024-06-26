package goorm.spoco.domain.code.controller.request;

public record CodeModifyDto(
        Long codeId,
        String language,
        String detail
) {
}
