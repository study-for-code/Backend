package goorm.spoco.domain.image.controller.request;

import lombok.Builder;

@Builder
public record ImageRequestDto(
        Long studyId
) {
}
