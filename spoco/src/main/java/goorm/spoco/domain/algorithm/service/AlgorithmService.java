package goorm.spoco.domain.algorithm.service;

import goorm.spoco.domain.algorithm.controller.request.AlgorithmRequestDto;
import goorm.spoco.domain.algorithm.controller.request.AlgorithmSearchDto;
import goorm.spoco.domain.algorithm.controller.response.AlgorithmResponseDto;
import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.code.repository.CodeRepository;
import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.join.repository.JoinRepository;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.study.repository.StudyRepository;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import goorm.spoco.domain.subscribe.repository.SubscribeRepository;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import goorm.spoco.infra.compiler.dto.ResultStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlgorithmService {

    private final AlgorithmRepository algorithmRepository;
    private final JoinRepository joinRepository;
    private final CodeRepository codeRepository;
    private final SubscribeRepository subscribeRepository;

    @Transactional
    public AlgorithmResponseDto create(AlgorithmRequestDto algorithmRequestDto) {
        Algorithm algorithm = algorithmRepository.save(Algorithm.create(algorithmRequestDto));
        algorithm.updateTitle(algorithm);
        return AlgorithmResponseDto.simple(algorithm);
    }

    @Transactional
    public void delete(Long algorithmId) {
        Algorithm algorithm = existByAlgorithmId(algorithmId);

        algorithm.delete();
    }

    @Transactional
    public AlgorithmResponseDto update(Long algorithmId, AlgorithmRequestDto algorithmRequestDto) {
        Algorithm algorithm = existByAlgorithmId(algorithmId);

        algorithm.updateInfo(algorithmRequestDto);
        return AlgorithmResponseDto.detail(algorithm);
    }

    public AlgorithmResponseDto getByAlgorithmId(Long algorithmId) {
        Algorithm algorithm = existByAlgorithmId(algorithmId);
        return AlgorithmResponseDto.detail(algorithm);
    }

    public List<AlgorithmResponseDto> getAll() {
        return algorithmRepository.findAllByStatus(Status.ACTIVE).stream()
                .map(AlgorithmResponseDto::detail).collect(Collectors.toList());
    }

    public List<AlgorithmResponseDto> getAllWithSolvedMember(Long studyId) {
        List<Algorithm> algorithms = algorithmRepository.findAllByStatus(Status.ACTIVE);

        return getSolvedMembersAndSubStatus(studyId, algorithms);
    }


    //== 숫자로 면 문제번호로 검색. 제목이면 제목% 검색 이후 없다면, %제목% 으로 검색 ==//
    public List<AlgorithmResponseDto> searchAlgorithms(String title, Long studyId) {
        // 들어온 title 이 숫자로만 이루어져 있으면 true
        if (title.matches("\\d+")) {
            List<Algorithm> algorithms = algorithmRepository.findAlgorithmsByTitleLikeAndStatus(title + "%", Status.ACTIVE);
            return getSolvedMembersAndSubStatus(studyId, algorithms);

        } else {
            // title% 이 존재하지 않는다면 %title% 로 반환. 그래도 없으면 빈 List 반환
            if (algorithmRepository.findAlgorithmsByOnlyTitle(title + "%", Status.ACTIVE).isEmpty()) {
                List<Algorithm> algorithms = algorithmRepository.findAlgorithmsByTitleLikeAndStatus("%" + title + "%", Status.ACTIVE);
                return getSolvedMembersAndSubStatus(studyId, algorithms);

            } else {
                List<Algorithm> algorithms = algorithmRepository.findAlgorithmsByOnlyTitle(title + "%", Status.ACTIVE);
                return getSolvedMembersAndSubStatus(studyId, algorithms);
            }
        }
    }

    private Algorithm existByAlgorithmId(Long algorithmId) {
        return algorithmRepository.findByAlgorithmIdAndStatus(algorithmId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, algorithmId + "에 해당하는 알고리즘이 존재하지 않습니다."));
    }

    private List<AlgorithmResponseDto> getSolvedMembersAndSubStatus(Long studyId, List<Algorithm> algorithms) {
        List<Subscribe> subscribes = subscribeRepository.findAllByCategory_Study_StudyIdAndStatus(studyId, Status.ACTIVE);

        List<Join> joins = joinRepository.findAllByStudy_StudyIdAndStatus(studyId, Status.ACTIVE);

        List<AlgorithmResponseDto> result = algorithms.stream()
                .map(algorithm -> {
                    List<MemberResponseDto> solvedMembers = joins.stream()
                            .map(join -> codeRepository.findByAlgorithm_AlgorithmIdAndMember_MemberIdAndStatus(algorithm.getAlgorithmId(), join.getMember().getMemberId(), Status.ACTIVE)
                                    .filter(code -> code.getAnswerType().equals(ResultStatus.PASS))
                                    .map(code -> MemberResponseDto.from(join.getMember()))
                                    .orElse(null)
                            )
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

                    boolean check = subscribes.stream()
                            .anyMatch(subscribe -> subscribe.getAlgorithm().getAlgorithmId().equals(algorithm.getAlgorithmId()));

                    return AlgorithmResponseDto.all(algorithm, solvedMembers, check);
                })
                .collect(Collectors.toList());
        return result;
    }
}
