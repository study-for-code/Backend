package goorm.chat.service;

import goorm.chat.domain.Message;
import goorm.chat.domain.MessageStatus;
import goorm.chat.dto.MessageDto;
import goorm.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageDto saveMessage(MessageDto messageDto) {
        return MessageDto.from(
                messageRepository.save(Message.create(messageDto))
        );

    }
    public List<MessageDto> getCodeIdMessage(Long codeId) {
        return messageRepository.findAllByCodeIdAndMessageStatus(codeId, MessageStatus.ACTIVE)
                .stream().map(MessageDto::from).collect(Collectors.toList());
    }

    public List<MessageDto> getReviewIdMessage(Long reviewId) {
        return messageRepository.findAllByReviewIdAndMessageStatus(reviewId, MessageStatus.ACTIVE)
                .stream().map(MessageDto::from).collect(Collectors.toList());
    }
}
