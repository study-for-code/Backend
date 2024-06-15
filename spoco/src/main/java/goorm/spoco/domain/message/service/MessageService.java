package goorm.spoco.domain.message.service;

import goorm.spoco.domain.message.controller.request.MessageRequestDto;
import goorm.spoco.domain.message.domain.Message;
import goorm.spoco.domain.message.repository.MessageRepository;
import goorm.spoco.domain.message.controller.request.CursorRequestDto;
import goorm.spoco.domain.message.controller.response.MessageResponseDto;
import goorm.spoco.global.common.response.Status;
import lombok.RequiredArgsConstructor;
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
    public MessageResponseDto saveMessage(MessageRequestDto messageRequestDto) {
        return MessageResponseDto.from(
                messageRepository.save(Message.create(messageRequestDto))
        );
    }

    public List<MessageResponseDto> findMessageWithReviewId(Long reviewId) {
        return messageRepository.findAllByReviewIdAndMessageStatusOrderByCreateAtDescIdDesc(reviewId, Status.ACTIVE)
                .stream().map(MessageResponseDto::from).collect(Collectors.toList());
    }

    public List<MessageResponseDto> findMessageWithCodeId(Long codeId) {
        return messageRepository.findAllByCodeIdAndMessageStatusOrderByCreateAtDescIdDesc(codeId, Status.ACTIVE)
                .stream().map(MessageResponseDto::from).collect(Collectors.toList());
    }

    public List<MessageResponseDto> findWithCursorPagination(CursorRequestDto cursorRequestDto) {

        Long reviewId = cursorRequestDto.reviewId();
        String lastId = cursorRequestDto.lastId();
        LocalDateTime lastTimestamp = cursorRequestDto.timestamp();

        Criteria criteria = Criteria.where("reviewId").is(reviewId)
                .and("MessageStatus").is(Status.ACTIVE);

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
                .stream().map(MessageResponseDto::from).collect(Collectors.toList());
    }
}
