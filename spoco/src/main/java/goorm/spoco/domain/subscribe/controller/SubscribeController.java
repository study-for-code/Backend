package goorm.spoco.domain.subscribe.controller;

import goorm.spoco.domain.subscribe.controller.request.SubscribeRequestDto;
import goorm.spoco.domain.subscribe.controller.request.SubscribeSubmitDto;
import goorm.spoco.domain.subscribe.controller.response.SubscribeResponseDto;
import goorm.spoco.domain.subscribe.service.SubscribeService;
import goorm.spoco.global.common.auth.SpocoUserDetails;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/subscribes")
    public BaseResponse subscribe(
            @RequestBody SubscribeRequestDto subscribeRequestDto
    ) {
        return BaseResponse.builder()
                .message("구독 서비스 실행")
                .results(List.of(subscribeService.subscribe(subscribeRequestDto.categoryId(), subscribeRequestDto.algorithmId())))
                .build();
    }

    @DeleteMapping("/subscribes/{subscribeId}")
    public BaseResponse cancel(
            @PathVariable Long subscribeId
    ) {
        subscribeService.cancel(subscribeId);
        return BaseResponse.builder()
                .message("구독 취소 실행")
                .build();
    }

    @GetMapping("/subscribes/{subscribesId}/subscribe")
    public BaseResponse getAlgorithmBySubscribeId(@PathVariable Long subscribesId) {
        return BaseResponse.builder()
                .message("구독된 단일 알고리즘 조회")
                .results(List.of(subscribeService.getAlgorithmBySubscribeId(subscribesId)))
                .build();
    }

    @GetMapping("/subscribes/submit")
    public BaseResponse getSubmitMembers(@RequestBody SubscribeSubmitDto subscribeSubmitDto) {
        return BaseResponse.builder()
                .message("제출자 반환")
                .results(List.of(subscribeService.getSubmitMembers(subscribeSubmitDto)))
                .build();
    }

    @GetMapping("/subscribes/{categoryId}/category")
    public BaseResponse getAllByCategoryId(
            @PathVariable Long categoryId
    ) {
        return BaseResponse.<SubscribeResponseDto>builder()
                .message("카테고리에 해당하는 알고리즘 반환")
                .results(subscribeService.getByCategoryId(categoryId))
                .build();
    }
}
