package goorm.chat.service;

import goorm.chat.config.MessageTestConfiguration;
import goorm.chat.dto.MessageDto;
import goorm.chat.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({MessageTestConfiguration.class})
class MessageServiceTest {

    @Autowired MessageService messageService;
    @Autowired MessageRepository messageRepository;

    @BeforeEach
    public void setUp() {
        messageRepository.deleteAll();

        for (int i = 0; i < 3; i++) {
            MessageDto messageDto1 = new MessageDto(null,1L, 1L, 1L, "leeho", "hi" + i, null);
            MessageDto savedMessage1 = messageService.saveMessage(messageDto1);

            MessageDto messageDto2 = new MessageDto(null, 2L, 1L, 1L, "jeonghea", "hum" + i, savedMessage1.timestamp());
            messageService.saveMessage(messageDto2);

            MessageDto messageDto3 = new MessageDto(null, 3L, 1L, 1L, "kimtea", "bye" + i, null);
            messageService.saveMessage(messageDto3);
        }
    }

    @AfterEach
    public void end() {
        messageRepository.deleteAll();
    }
}