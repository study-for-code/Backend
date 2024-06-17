package goorm.spoco.domain.testcase.service;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.testcase.controller.request.TestcaseRequestDto;
import goorm.spoco.domain.testcase.controller.response.TestcaseResponseDto;
import goorm.spoco.domain.testcase.domain.Testcase;
import goorm.spoco.domain.testcase.repository.TestcaseRepository;
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
public class TestcaseService {

    private final TestcaseRepository testcaseRepository;
    private final AlgorithmRepository algorithmRepository;

    @Transactional
    public TestcaseResponseDto create(TestcaseRequestDto testcaseRequestDto, Long algorithmId) {
        Algorithm algorithm = algorithmRepository.findByAlgorithmIdAndStatus(algorithmId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.DUPLICATE_OBJECT, algorithmId + "에 해당하는 알고리즘이 존재하지 않습니다."));

        Testcase testcase = testcaseRepository.save(Testcase.create(testcaseRequestDto, algorithm));
        return TestcaseResponseDto.from(testcase);
    }

    @Transactional
    public void delete(Long testcaseId) {
        Testcase testcase = testcaseRepository.findByTestcaseIdAndStatus(testcaseId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, testcaseId + "에 해당하는 테스트케이스가 존재하지 않습니다."));

        testcase.delete();
    }

    @Transactional
    public TestcaseResponseDto update(Long testcaseId, TestcaseRequestDto testcaseRequestDto) {
        Testcase testcase = testcaseRepository.findByTestcaseIdAndStatus(testcaseId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, testcaseId + "에 해당하는 테스트케이스가 존재하지 않습니다."));

        testcase.updateInfo(testcaseRequestDto);

        return TestcaseResponseDto.from(testcase);
    }

    public List<TestcaseResponseDto> getAllByAlgorithmId(Long algorithmId) {
        return testcaseRepository.findAllByAlgorithm_AlgorithmIdAndStatus(algorithmId, Status.ACTIVE)
                .stream().map(TestcaseResponseDto::from).collect(Collectors.toList());
    }
}
