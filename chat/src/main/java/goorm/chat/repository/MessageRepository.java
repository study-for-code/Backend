package goorm.chat.repository;

import goorm.chat.domain.Message;
import goorm.chat.domain.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findAllByReviewIdAndMessageStatusOrderByCreateAtDescIdDesc(Long reviewId, MessageStatus messageStatus);
    List<Message> findAllByCodeIdAndMessageStatusOrderByCreateAtDescIdDesc(Long codeId, MessageStatus messageStatus);
}
