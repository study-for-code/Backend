package goorm.spoco.domain.image.controller.request;

import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record ImageRequestDto(
        MultipartFile file,
        Long studyId,
        String title
) {
    public ImageRequestDto {
        if (file.isEmpty()) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "이미지가 첨부되지 않았습니다.");
        }
    }
}
