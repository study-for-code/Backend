package goorm.chat.service;

import goorm.chat.domain.Message;
import goorm.chat.dto.MessageDto;
import goorm.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public MessageDto saveMessage(MessageDto messageDto) {
        return MessageDto.from(
                messageRepository.save(Message.create(messageDto))
        );
    }
}
