package goorm.chat.controller;

import goorm.chat.dto.MessageDto;
import goorm.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageDto messageDto) {
        MessageDto saveMessage = messageService.saveMessage(messageDto);

        simpMessagingTemplate.convertAndSend("/topic/chat/" + saveMessage.reviewId() + "/" + saveMessage.codeLine(), saveMessage);
    }
}
