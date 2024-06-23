package goorm.spoco.domain.image.controller.response;

import goorm.spoco.domain.image.domain.Image;
import goorm.spoco.domain.image.repository.ImageRepository;
import goorm.spoco.domain.study.domain.Study;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public record ImageResponseDto(
        Long imageId,
        String imageFileUrl
) {

    public static ImageResponseDto from(Image image) {
        return new ImageResponseDto(
            image.getImageId(),
            image.getImageFileUrl()
        );
    }
}
