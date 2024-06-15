package goorm.spoco.domain.message.repository;

import goorm.spoco.domain.message.domain.Message;
import goorm.spoco.global.common.response.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, Long> {
    List<Message> findAllByReviewIdAndMessageStatusOrderByCreateAtDescIdDesc(Long reviewId, Status messageStatus);
    List<Message> findAllByCodeIdAndMessageStatusOrderByCreateAtDescIdDesc(Long codeId, Status messageStatus);
}
