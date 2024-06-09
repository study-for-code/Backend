package goorm.spoco.domain.testcase.service;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.testcase.domain.Testcase;
import goorm.spoco.domain.testcase.repository.TestcaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestcaseService {

    private final TestcaseRepository testcaseRepository;
    private final AlgorithmRepository algorithmRepository;

    public Testcase save(Testcase testcase, Long algorithmId) {
        Algorithm algorithm = algorithmRepository.findById(algorithmId)
                .orElseThrow(() -> new IllegalArgumentException(algorithmId + "에 해당하는 알고리즘이 존재하지 않습니다."));

        testcase.addAlgorithm(algorithm); // 알고리즘에 testcase 추가
        return testcaseRepository.save(testcase); // testcase DB에 testcase 추가
    }

    public void delete(Long testcaseId) {
        Testcase testcase = testcaseRepository.findById(testcaseId)
                .orElseThrow(() -> new IllegalArgumentException(testcaseId + "에 해당하는 테스트케이스가 존재하지 않습니다."));
        testcaseRepository.delete(testcase);
    }
}
