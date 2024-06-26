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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDY_ID")
    private Study study;

    private String imageFileUrl;

    public void addStudy(Study study) {
        this.study = study;
        study.setImage(this);
    }

    public static Image create(Study study, String imageFileName) {
        Image image = new Image();
        image.addStudy(study);
        image.imageFileUrl = imageFileName;
        return image;
    }

    public void updateUrl(String imageFileUrl) {
        this.imageFileUrl = imageFileUrl;
    }
}
