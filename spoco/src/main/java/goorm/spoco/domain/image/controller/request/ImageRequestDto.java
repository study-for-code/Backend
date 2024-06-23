package goorm.spoco.domain.image.controller.request;

import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record ImageRequestDto(
        Long studyId,
        String title
) {
}
