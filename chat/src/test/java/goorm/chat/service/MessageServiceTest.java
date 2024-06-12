package goorm.chat.service;

import goorm.chat.domain.Message;
import goorm.chat.dto.MessageDto;
import goorm.chat.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
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

    @Test
    public void 커서_페이징_처리() throws Exception {
        //given

        //when
        List<MessageDto> page = messageService.findWithCursorPagination(1L, null, null);
        while (page.size() >= 5) {
            page = messageService.findWithCursorPagination(1L, page.get(page.size()-1).timestamp(), page.get(page.size()-1).messageId());
        }

        //then
        assertEquals(page.get(page.size()-1).content(), "hi0");
    }

    @Test
    public void 리뷰_전부_가져오기() throws Exception {
        //given

        //when
        List<MessageDto> message = messageService.findMessageWithReviewId(1L);

        //then
        assertEquals(message.size(), 9);
        assertEquals(message.get(0).content(), "bye2");
    }

    @Test
    public void 코드_단위_리뷰_전부_가져오기() throws Exception {
        //given

        //when
        List<MessageDto> message = messageService.findMessageWithCodeId(1L);

        //then
        assertEquals(message.size(), 9);
        assertEquals(message.get(0).content(), "bye2");
    }
}