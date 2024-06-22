package goorm.spoco.domain.image.domain;

import goorm.spoco.domain.image.controller.request.ImageRequestDto;
import goorm.spoco.domain.study.domain.Study;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long imageId;

    private Long studyId;

    private String title;

    private String imageFileUrl;

    public static Image create(ImageRequestDto imageRequestDto, String imageFileName) {
        Image image = new Image();
        image.studyId = imageRequestDto.studyId();
        image.title = imageRequestDto.title();
        image.imageFileUrl = imageFileName;
        return image;
    }
}
