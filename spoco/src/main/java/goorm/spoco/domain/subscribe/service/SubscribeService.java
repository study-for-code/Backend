package goorm.spoco.domain.subscribe.service;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.domain.AlgorithmStatus;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.category.domain.CategoryStatus;
import goorm.spoco.domain.category.repository.CategoryRepository;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import goorm.spoco.domain.subscribe.repository.SubscribeRepository;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import goorm.spoco.global.status.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final CategoryRepository categoryRepository;
    private final AlgorithmRepository algorithmRepository;

    public Subscribe subscribe(Long categoryId, Long algorithmId) {
        Category category = categoryRepository.findByCategoryIdAndCategoryStatus(categoryId, CategoryStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, categoryId + "에 해당하는 카테고리가 존재하지 않습니다."));
        Algorithm algorithm = algorithmRepository.findAlgorithmByAlgorithmIdAndAlgorithmStatus(algorithmId, AlgorithmStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, algorithmId + "에 해당하는 알고리즘이 존재하지 않습니다."));
        Subscribe subscribe = Subscribe.subscribe(category, algorithm);
        return subscribeRepository.save(subscribe);
    }

    public void cancelSubscribe(Long id) {
        Subscribe subscribe = subscribeRepository.findSubscribeBySubscribeIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, id + "에 해당하는 구독이 존재하지 않습니다."));

        Long algorithmId = subscribe.getAlgorithm().getAlgorithmId();
        Long categoryId = subscribe.getCategory().getCategoryId();

        Category category = categoryRepository.findByCategoryIdAndCategoryStatus(categoryId, CategoryStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, categoryId + "에 해당하는 카테고리가 존재하지 않습니다."));
        Algorithm algorithm = algorithmRepository.findAlgorithmByAlgorithmIdAndAlgorithmStatus(algorithmId, AlgorithmStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, algorithmId + "에 해당하는 알고리즘이 존재하지 않습니다."));

        subscribe.delete(category,algorithm);
    }

}
