package goorm.spoco.domain.image.service;

import goorm.spoco.domain.image.controller.request.ImageRequestDto;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final StudyRepository studyRepository;

    @Value("${image.file.path}")
    private String folder;

    public Image imageUpload(ImageRequestDto imageRequestDto) {
        Study study = studyRepository.findByStudyIdAndStatus(imageRequestDto.studyId(), Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 스터디를 찾을 수 없습니다."));

        // 파일 이름
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageRequestDto.file().getOriginalFilename();

        // 스터디 이름에 맞게 파일경로에 파일 생성
        Path imageFilePath = Paths.get(folder + study.getTitle() + "/" + imageFileName);

        try {
            // 혹시 서버가 내려가서 경로가 사라지면 경로에 폴더를 만들어줌.
            if (!Files.exists(Paths.get(folder))) {
                Files.createDirectories(Paths.get(folder));
            }
            Files.write(imageFilePath, imageRequestDto.file().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.GENERAL_ERROR, "이미지 파일 저장에 실패했습니다.");
        }

        Image image = Image.create(imageRequestDto, imageFileName);
        return imageRepository.save(image);
    }

    public List<Image> getAllImagesByStudyId(Long studyId) {
        return imageRepository.findImagesByStudyId(studyId);
    }

    public Image getImageByImageId(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, imageId + "에 해당하는 이미지가 존재하지 않습니다."));
    }


}
