package goorm.spoco.domain.code.controller;

import goorm.spoco.domain.code.controller.request.CodeRequestDto;
import goorm.spoco.domain.code.service.CodeService;
import goorm.spoco.global.common.auth.SpocoUserDetails;
import goorm.spoco.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CodeController {

    private final CodeService codeService;
    @PostMapping("/submit/{algorithmId}")
    public BaseResponse submit(
            @PathVariable Long algorithmId,
            @RequestBody CodeRequestDto codeRequestDto,
            @AuthenticationPrincipal SpocoUserDetails user
    ) {
        return BaseResponse.builder()
                .message("제출 성공")
                .results(List.of(codeService.submit(codeRequestDto, algorithmId, user.getMemberId())))
                .build();
    }

    @GetMapping("/codes/{algorithmId}")
    public BaseResponse getMyCodeByAlgorithmId(
            @PathVariable Long algorithmId,
            @AuthenticationPrincipal SpocoUserDetails user
    ) {
        return BaseResponse.builder()
                .message("내 코드 가져오기")
                .results(List.of(codeService.getCodeByAlgorithmId(algorithmId, user.getMemberId())))
                .build();
    }
}
