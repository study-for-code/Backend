package goorm.spoco.domain.image.controller;

import goorm.spoco.domain.image.controller.request.ImageRequestDto;
import goorm.spoco.domain.image.domain.Image;
import goorm.spoco.domain.image.service.ImageService;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/images")
    public BaseResponse upload(@RequestBody ImageRequestDto imageRequestDto) {
        return BaseResponse.builder()
                .message("이미지 업로드 성공")
                .results(List.of(imageService.imageUpload(imageRequestDto)))
                .build();
    }

    @GetMapping("/images/study/{studyId}")
    public BaseResponse getImageList(Long studyId) {
        List<Image> imageList = imageService.getAllImagesByStudyId(studyId);
        return BaseResponse.<Image>builder()
                .message("이미지 리스트 조회 성공")
                .results(imageList)
                .build();
    }

    @GetMapping("images/{imageId}")
    public BaseResponse getImage(Long imageId) {
        Image image = imageService.getImageByImageId(imageId);
        return BaseResponse.builder()
                .message("이미지 조회 성공")
                .results(List.of(image))
                .build();
    }
}
