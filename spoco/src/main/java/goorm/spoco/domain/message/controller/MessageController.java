package goorm.spoco.domain.message.controller;

import goorm.spoco.domain.message.controller.request.CursorRequestDto;
import goorm.spoco.domain.message.controller.response.MessageResponseDto;
import goorm.spoco.domain.message.service.MessageService;
import goorm.spoco.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/messages/{reviewId}/review")
    public BaseResponse getReviewMessage(@PathVariable Long reviewId) {
        return BaseResponse.<MessageResponseDto>builder()
                .message("리뷰 전체 메시지 가져오기 성공")
                .results(messageService.findMessageWithReviewId(reviewId))
                .build();
    }

    @GetMapping("/messages/cursor")
    public BaseResponse getReviewMessageWithCursor(@RequestBody CursorRequestDto cursorRequestDto) {
        return BaseResponse.<MessageResponseDto>builder()
                .message("리뷰 메시지 커서 기반 페이징 성공")
                .results(messageService.findWithCursorPagination(cursorRequestDto))
                .build();
    }

    @GetMapping("/messages/{codeId}/code")
    public BaseResponse getMessageInCode(@PathVariable Long codeId) {
        return BaseResponse.<MessageResponseDto>builder()
                .message("코드 전체 메시지 가져오기 성공")
                .results(messageService.findMessageWithCodeId(codeId))
                .build();
    }

}
