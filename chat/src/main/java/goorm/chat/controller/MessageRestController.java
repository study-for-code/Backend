package goorm.chat.controller;

import goorm.chat.dto.MessageDto;
import goorm.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageRestController {

    private final MessageService messageService;

    @GetMapping("/message/{reviewId}")
    public List<MessageDto> getReviewMessage(@PathVariable Long reviewId) {
        return messageService.getReviewIdMessage(reviewId);
    }
}
