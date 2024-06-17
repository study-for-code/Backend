package goorm.spoco.domain.algorithm.service;

import goorm.spoco.domain.algorithm.controller.request.AlgorithmRequestDto;
import goorm.spoco.domain.algorithm.controller.response.AlgorithmResponseDto;
import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlgorithmService {

    private final AlgorithmRepository algorithmRepository;

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
        return algorithmRepository.findAll()
                .stream().map(AlgorithmResponseDto::detail).collect(Collectors.toList());
    }

    //== 숫자로 면 문제번호로 검색. 제목이면 제목% 검색 이후 없다면, %제목% 으로 검색 ==//
    public List<AlgorithmResponseDto> searchAlgorithms(String title) {
        // 들어온 title 이 숫자로만 이루어져 있으면 true
        if (title.matches("\\d+")) {
            return algorithmRepository.findAlgorithmsByTitleLikeAndStatus(title + "%", Status.ACTIVE)
                    .stream().map(AlgorithmResponseDto::simple).collect(Collectors.toList());
        } else {
            // title% 이 존재하지 않는다면 %title% 로 반환. 그래도 없으면 빈 List 반환
            if (algorithmRepository.findAlgorithmsByOnlyTitle(title + "%", "ACTIVE").isEmpty()) {
                return algorithmRepository.findAlgorithmsByTitleLikeAndStatus("%" + title + "%", Status.ACTIVE)
                        .stream().map(AlgorithmResponseDto::simple).collect(Collectors.toList());
            } else {
                return algorithmRepository.findAlgorithmsByOnlyTitle(title + "%", "ACTIVE")
                        .stream().map(AlgorithmResponseDto::simple).collect(Collectors.toList());
            }
        }
    }

    public List<Algorithm> searchAlgorithmsByNum(String num) {
        return algorithmRepository.findAlgorithmsByTitleLikeAndStatus(num + "%", Status.ACTIVE);
    }

    private Algorithm existByAlgorithmId(Long algorithmId) {
        return algorithmRepository.findByAlgorithmIdAndStatus(algorithmId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, algorithmId + "에 해당하는 알고리즘이 존재하지 않습니다."));
    }

}
