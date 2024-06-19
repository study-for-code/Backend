package goorm.spoco.domain.subscribe.service;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.category.repository.CategoryRepository;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.code.repository.CodeRepository;
import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.join.repository.JoinRepository;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.domain.study.repository.StudyRepository;
import goorm.spoco.domain.subscribe.controller.request.SubscribeSubmitDto;
import goorm.spoco.domain.subscribe.controller.response.SubscribeResponseDto;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import goorm.spoco.domain.subscribe.repository.SubscribeRepository;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final CategoryRepository categoryRepository;
    private final AlgorithmRepository algorithmRepository;
    private final JoinRepository joinRepository;
    private final CodeRepository codeRepository;
    private final StudyRepository studyRepository;

    @Transactional
    public void subscribe(Long categoryId, Long algorithmId) {
        Category category = categoryRepository.findByCategoryIdAndStatus(categoryId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, categoryId + "에 해당하는 카테고리가 존재하지 않습니다."));

        Algorithm algorithm = algorithmRepository.findByAlgorithmIdAndStatus(algorithmId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, algorithmId + "에 해당하는 알고리즘이 존재하지 않습니다."));

        algorithmValidateDuplication(category, algorithm);


//        for ( Category c : study.getCategories() ) {
//
//            for ( Subscribe s : c.getSubscribes() ) {
//
//                if (s.getAlgorithm().equals(algorithm)) {
//                    throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 구독된 알고리즘입니다.");
//                }
//            }
//        }

        subscribeRepository.save(Subscribe.subscribe(category, algorithm));
    }

    private void algorithmValidateDuplication(Category category, Algorithm algorithm) {
        Study study = studyRepository.findByStudyIdAndStatus(category.getStudy().getStudyId(), Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 스터디가 존재하지 않습니다."));

        study.getCategories().stream()
                .map(c -> c.getSubscribes().stream()
                        .filter(subscribe -> subscribe.getAlgorithm().equals(algorithm)))
                .findFirst()
                .ifPresent(subscribe -> {
                    throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 구독된 알고리즘입니다.");
                });
    }

    @Transactional
    public void cancel(Long subscribeId) {
        Subscribe subscribe = subscribeRepository.findBySubscribeIdAndStatus(subscribeId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, subscribeId + "에 해당하는 구독이 존재하지 않습니다."));

        subscribe.delete();
    }

    public List<SubscribeResponseDto> getByCategoryId(Long categoryId) {
        return subscribeRepository.findAllByCategory_CategoryIdAndStatus(categoryId, Status.ACTIVE)
                .stream().map(SubscribeResponseDto::simple).collect(Collectors.toList());
    }

    public SubscribeResponseDto getSubmitMembers(SubscribeSubmitDto subscribeSubmitDto) {
        Subscribe subscribe = subscribeRepository.findBySubscribeIdAndStatus(subscribeSubmitDto.subscribeId(), Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "구독되어 있지 않습니다."));


        List<Join> joins = joinRepository.findAllByStudy_StudyIdAndStatus(subscribeSubmitDto.studyId(), Status.ACTIVE);

        List<MemberResponseDto> submitted = joins.stream()
                .map(join -> {
                    Optional<Code> OptionalCode = codeRepository.findByAlgorithm_AlgorithmIdAndMember_MemberIdAndStatus(subscribe.getAlgorithm().getAlgorithmId(), join.getMember().getMemberId(), Status.ACTIVE);
                    return OptionalCode.map(code -> MemberResponseDto.review(join, code))
                            .orElse(MemberResponseDto.review(join, null));
                })
                .collect(Collectors.toList());

        return SubscribeResponseDto.from(subscribe, submitted);
    }
}
