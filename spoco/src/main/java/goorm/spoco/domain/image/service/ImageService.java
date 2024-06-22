package goorm.spoco.domain.image.service;

import goorm.spoco.domain.image.controller.request.ImageRequestDto;
import goorm.spoco.domain.image.domain.Image;
import goorm.spoco.domain.image.repository.ImageRepository;
import goorm.spoco.domain.study.repository.StudyRepository;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final StudyRepository studyRepository;

    @Value("${image.file.path}")
    private String folder;

    public Image imageUpload(ImageRequestDto imageRequestDto) {
        studyRepository.findByStudyIdAndStatus(imageRequestDto.studyId(), Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 스터디를 찾을 수 없습니다."));

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageRequestDto.file().getOriginalFilename();

        Path imageFilePath = Paths.get(folder + imageFileName);

        try {
            Files.write(imageFilePath, imageRequestDto.file().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Image image = Image.create(imageRequestDto, imageFileName);
        return imageRepository.save(image);
    }
}
