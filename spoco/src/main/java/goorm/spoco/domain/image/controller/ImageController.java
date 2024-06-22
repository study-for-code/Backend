package goorm.spoco.domain.image.controller;

import goorm.spoco.domain.image.controller.request.ImageRequestDto;
import goorm.spoco.domain.image.repository.ImageRepository;
import goorm.spoco.domain.image.service.ImageService;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

//    @GetMapping("/images/{studyId}")
//    public BaseResponse getImage(Long studyId) {
//        return BaseResponse.builder()
//                .message("이미지 조회 성공")
//                .results(imageService)
//    }
}
