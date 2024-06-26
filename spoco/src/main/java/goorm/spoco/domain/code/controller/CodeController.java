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
    @PutMapping("/submit")
    public BaseResponse submit(
            @RequestBody CodeRequestDto codeRequestDto,
            @AuthenticationPrincipal SpocoUserDetails user
    ) {
        return BaseResponse.builder()
                .message("제출 성공")
                .results(List.of(codeService.submit(codeRequestDto, user.getMemberId())))
                .build();
    }

//    @PatchMapping("/codes/{codeId}")
//    public BaseResponse modify(
//            @PathVariable Long codeId,
//            @RequestBody CodeRequestDto codeRequestDto
//    ) {
//        return BaseResponse.builder()
//                .message("제출 성공")
//                .results(List.of(codeService.modify(codeRequestDto, codeId)))
//                .build();
//    }

    @GetMapping("/codes/{algorithmId}")
    public BaseResponse getMyCode(
            @PathVariable Long algorithmId,
            @RequestParam Long memberId
    ) {
        return BaseResponse.builder()
                .message("코드 가져오기")
                .results(List.of(codeService.getCode(algorithmId, memberId)))
                .build();
    }
}
