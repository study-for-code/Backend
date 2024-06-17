package goorm.spoco.domain.subscribe.controller;

import goorm.spoco.domain.subscribe.controller.request.SubscribeRequestDto;
import goorm.spoco.domain.subscribe.controller.response.SubscribeResponseDto;
import goorm.spoco.domain.subscribe.service.SubscribeService;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/subscribes")
    public BaseResponse subscribe(
            @RequestBody SubscribeRequestDto subscribeRequestDto
    ) {
        subscribeService.subscribe(subscribeRequestDto.categoryId(), subscribeRequestDto.algorithmId());
        return BaseResponse.builder()
                .message("구독 서비스 실행")
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

    @GetMapping("/subscribes/{categoryId}")
    public BaseResponse getAllByCategoryId(
            @PathVariable Long categoryId
    ) {
        return BaseResponse.<SubscribeResponseDto>builder()
                .message("카테고리에 해당하는 알고리즘 반환")
                .results(subscribeService.getByCategoryId(categoryId))
                .build();
    }
}
