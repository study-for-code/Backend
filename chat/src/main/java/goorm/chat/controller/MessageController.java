package goorm.chat.controller;

import goorm.chat.dto.MessageDto;
import goorm.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload MessageDto messageDto) {
        MessageDto saveMessage = messageService.saveMessage(messageDto);

        simpMessagingTemplate.convertAndSend("/topic/" + saveMessage.reviewId(), saveMessage);

//        simpMessagingTemplate.convertAndSend(
//                "/topic/" + messageDto.reviewId(), messageDto);
    }
}
