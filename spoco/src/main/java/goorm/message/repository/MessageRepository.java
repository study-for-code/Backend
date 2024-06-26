package goorm.message.repository;

import goorm.message.domain.Message;
import goorm.spoco.global.common.response.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, Long> {
    List<Message> findAllByReviewIdAndStatusOrderByCreateAtDescIdDesc(Long reviewId, Status messageStatus);
    List<Message> findAllByCodeIdAndStatusOrderByCreateAtDescIdDesc(Long codeId, Status messageStatus);
}
