package goorm.spoco.domain.subscribe.service;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.domain.AlgorithmStatus;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.category.domain.CategoryStatus;
import goorm.spoco.domain.category.repository.CategoryRepository;
import goorm.spoco.domain.subscribe.controller.response.SubscribeResponseDto;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import goorm.spoco.domain.subscribe.repository.SubscribeRepository;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final CategoryRepository categoryRepository;
    private final AlgorithmRepository algorithmRepository;

    public void subscribe(Long categoryId, Long algorithmId) {
        Category category = categoryRepository.findByCategoryIdAndStatus(categoryId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, categoryId + "에 해당하는 카테고리가 존재하지 않습니다."));

        Algorithm algorithm = algorithmRepository.findByAlgorithmIdAndStatus(algorithmId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, algorithmId + "에 해당하는 알고리즘이 존재하지 않습니다."));

        subscribeRepository.save(Subscribe.subscribe(category, algorithm));
    }

    public void cancel(Long subscribeId) {
        Subscribe subscribe = subscribeRepository.findBySubscribeIdAndStatus(subscribeId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, subscribeId + "에 해당하는 구독이 존재하지 않습니다."));

        subscribe.delete();
    }

    public List<SubscribeResponseDto> getByCategoryId(Long categoryId) {
        return subscribeRepository.findAllByCategory_CategoryIdAndStatus(categoryId, Status.ACTIVE)
                .stream().map(SubscribeResponseDto::from).collect(Collectors.toList());
    }

}
