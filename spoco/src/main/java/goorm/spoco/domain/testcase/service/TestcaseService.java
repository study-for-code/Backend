package goorm.spoco.domain.testcase.service;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.testcase.domain.Testcase;
import goorm.spoco.domain.testcase.domain.TestcaseStatus;
import goorm.spoco.domain.testcase.dto.TestcaseDTO;
import goorm.spoco.domain.testcase.repository.TestcaseRepository;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional(readOnly = true)
@Transactional
@RequiredArgsConstructor
public class TestcaseService {

    private final TestcaseRepository testcaseRepository;
    private final AlgorithmRepository algorithmRepository;

    public Testcase save(Testcase testcase, Long algorithmId) {
        Algorithm algorithm = algorithmRepository.findById(algorithmId)
                .orElseThrow(() -> new CustomException(ErrorCode.DUPLICATE_OBJECT,algorithmId + "에 해당하는 알고리즘이 존재하지 않습니다."));

        testcase.addAlgorithm(algorithm); // 알고리즘에 testcase 추가
        return testcaseRepository.save(testcase); // testcase DB에 testcase 추가
    }

    public void delete(Long testcaseId) {
        Testcase testcase = testcaseRepository.findByTestcaseIdAndTestcaseStatus(testcaseId, TestcaseStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND,testcaseId + "에 해당하는 테스트케이스가 존재하지 않습니다."));
        // == sofe delete 로 변경해야함. ==//
        // testcaseRepository.delete(testcase);

        testcase.delete();
        testcaseRepository.save(testcase);
    }

    public Testcase update(Long testcaseId, TestcaseDTO testcaseDTO) {
        Testcase testcase = testcaseRepository.findByTestcaseIdAndTestcaseStatus(testcaseId, TestcaseStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, (testcaseId + "에 해당하는 테스트케이스가 존재하지 않습니다.")));

        testcase.setInput(testcaseDTO.getInput());
        testcase.setOutput(testcaseDTO.getOutput());

        return testcaseRepository.save(testcase);
    }
}
