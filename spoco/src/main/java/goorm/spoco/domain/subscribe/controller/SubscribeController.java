package goorm.spoco.domain.subscribe.controller;

import goorm.spoco.domain.subscribe.controller.request.SubscribeRequest;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import goorm.spoco.domain.subscribe.service.SubscribeService;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscribe")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping
    public BaseResponse subscribe(
            @RequestBody SubscribeRequest request
    ) {
        Subscribe subscribe = subscribeService.subscribe(request.getCategoryId(), request.getAlgorithmId());
        return BaseResponse.builder()
                .message("구독 서비스 실행")
                .results(List.of(subscribe, subscribe.getCategory(), subscribe.getAlgorithm()))
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseResponse cancelSubscribe(
            @PathVariable Long id
    ) {
        subscribeService.cancelSubscribe(id);
        return BaseResponse.builder()
                .message("구독 취소 실행")
                .build();
    }
}
