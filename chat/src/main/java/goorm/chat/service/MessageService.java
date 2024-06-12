package goorm.chat.service;

import goorm.chat.domain.Message;
import goorm.chat.domain.MessageStatus;
import goorm.chat.dto.MessageDto;
import goorm.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {
    static final int PAGE_SIZE = 5;

    private final MongoTemplate mongoTemplate;
    private final MessageRepository messageRepository;

    @Transactional
    public MessageDto saveMessage(MessageDto messageDto) {
        return MessageDto.from(
                messageRepository.save(Message.create(messageDto))
        );
    }

    public List<MessageDto> findMessageWithReviewId(Long reviewId) {
        return messageRepository.findAllByReviewIdAndMessageStatusOrderByCreateAtDescIdDesc(reviewId, MessageStatus.ACTIVE)
                .stream().map(MessageDto::from).collect(Collectors.toList());
    }

    public List<MessageDto> findMessageWithCodeId(Long codeId) {
        return messageRepository.findAllByCodeIdAndMessageStatusOrderByCreateAtDescIdDesc(codeId, MessageStatus.ACTIVE)
                .stream().map(MessageDto::from).collect(Collectors.toList());
    }

    public List<MessageDto> findWithCursorPagination(Long reviewId, LocalDateTime lastTimestamp, String lastId) {

        Criteria criteria = Criteria.where("reviewId").is(reviewId)
                .and("MessageStatus").is(MessageStatus.ACTIVE);

        if (lastId != null && lastTimestamp != null) {
            // 타임스탬프와 _id를 이용한 페이징
            Criteria timeCriteria = new Criteria().where("createAt").lte(lastTimestamp)
                    .and("_id").lt(lastId);

            criteria = new Criteria().andOperator(criteria, timeCriteria);
        }

        Query query = new Query(criteria)
                .with(Sort.by(Sort.Direction.DESC, "createAt")
                        .and(Sort.by(Sort.Direction.DESC, "_id")))
                .limit(PAGE_SIZE);

        return mongoTemplate.find(query, Message.class)
                .stream().map(MessageDto::from).collect(Collectors.toList());
    }
}
