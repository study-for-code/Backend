package goorm.spoco.domain.message.controller;

import goorm.spoco.domain.message.controller.request.MessageRequestDto;
import goorm.spoco.domain.message.controller.response.MessageResponseDto;
import goorm.spoco.domain.message.service.MessageService;
import goorm.spoco.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/message")
    public BaseResponse create(@PathVariable MessageRequestDto messageRequestDto) {
        MessageResponseDto message = messageService.createMessage(messageRequestDto);
        return BaseResponse.builder()
                .message("메시지 생성 성공")
                .results(List.of(message))
                .build();
    }
}
