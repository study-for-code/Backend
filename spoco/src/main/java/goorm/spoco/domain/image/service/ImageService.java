package goorm.spoco.domain.image.service;

import goorm.spoco.domain.image.controller.request.ImageRequestDto;
import goorm.spoco.domain.image.controller.response.ImageResponseDto;
import goorm.spoco.domain.image.domain.Image;
import goorm.spoco.domain.image.repository.ImageRepository;
import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.domain.study.repository.StudyRepository;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final StudyRepository studyRepository;

    @Value("${image.file.path}")
    private String folder;

    @Transactional
    public ImageResponseDto imageUpload(Long studyId, MultipartFile multipartFile) {
        Study study = studyRepository.findByStudyIdAndStatus(studyId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 스터디를 찾을 수 없습니다."));

        // 파일 이름
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + multipartFile.getOriginalFilename();

        // 스터디 이름에 맞게 파일경로에 파일 생성
        Path imageFilePath = Paths.get(folder + imageFileName);

        try {
            Files.write(imageFilePath, multipartFile.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.GENERAL_ERROR, "이미지 파일 저장에 실패했습니다.");
        }

        Image image = imageRepository.findByStudy_StudyId(studyId)
                        .orElseGet(() -> Image.create(study, imageFileName));

        if (image.getImageId() != null) {
            image.updateUrl(imageFileName);
        }

        Image save = imageRepository.save(image);

        return ImageResponseDto.from(save);
    }

    public ImageResponseDto getImageByStudyId(Long studyId) {
        Image image = imageRepository.findByStudy_StudyId(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, studyId + "에 해당하는 이미지가 존재하지 않습니다."));

        return ImageResponseDto.from(image);
    }


}
