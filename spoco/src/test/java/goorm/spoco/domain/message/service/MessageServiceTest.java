package goorm.spoco.domain.message.service;

import goorm.spoco.domain.message.config.MessageTestConfig;
import goorm.spoco.domain.message.controller.request.CursorRequestDto;
import goorm.spoco.domain.message.controller.request.MessageRequestDto;
import goorm.spoco.domain.message.controller.response.MessageResponseDto;
import goorm.spoco.domain.message.repository.MessageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MessageServiceTest {

    @Autowired MessageService messageService;
    @Autowired MessageRepository messageRepository;

    @BeforeEach
    public void setUp() {
        messageRepository.deleteAll();

        for (int i = 0; i < 3; i++) {
            MessageRequestDto message1 = new MessageRequestDto(1L, 1L, 1L, "leeho", "hi" + i);
            messageService.saveMessage(message1);

            MessageRequestDto message2 = new MessageRequestDto(2L, 1L, 1L, "jeonghea", "hum" + i);
            messageService.saveMessage(message2);

            MessageRequestDto message3 = new MessageRequestDto(3L, 1L, 1L, "kimtea", "bye" + i);
            messageService.saveMessage(message3);
        }
    }

    @AfterEach
    public void end() {
        messageRepository.deleteAll();
    }

    @Test
    public void 커서_페이징_처리() throws Exception {
        //given

        //when
        List<MessageResponseDto> page = messageService.findWithCursorPagination(new CursorRequestDto(1L, null, null));
        while (page.size() >= 5) {
            page = messageService.findWithCursorPagination(new CursorRequestDto(1L, page.get(page.size()-1).timestamp(), page.get(page.size()-1).messageId()));
        }

        //then
        assertEquals(page.get(page.size()-1).content(), "hi0");
    }

    @Test
    public void 리뷰_전부_가져오기() throws Exception {
        //given

        //when
        List<MessageResponseDto> message = messageService.findMessageWithReviewId(1L);

        //then
        assertEquals(message.size(), 9);
        assertEquals(message.get(0).content(), "bye2");
    }

    @Test
    public void 코드_단위_리뷰_전부_가져오기() throws Exception {
        //given

        //when
        List<MessageResponseDto> message = messageService.findMessageWithCodeId(1L);

        //then
        assertEquals(message.size(), 9);
        assertEquals(message.get(0).content(), "bye2");
    }
}