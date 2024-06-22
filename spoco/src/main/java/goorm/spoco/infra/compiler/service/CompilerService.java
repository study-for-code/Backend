package goorm.spoco.infra.compiler.service;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.code.controller.request.CodeRequestDto;
import goorm.spoco.domain.testcase.controller.response.TestcaseResponseDto;
import goorm.spoco.domain.testcase.repository.TestcaseRepository;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.infra.compiler.compiler.*;
import goorm.spoco.infra.compiler.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilerService {

    private final TestcaseRepository testcaseRepository;

    private final JavaCompiler javaCompiler;
    private final CppCompiler cppCompiler;
    private final PythonCompiler pythonCompiler;

    @Transactional
    public List<ResultDto> submitCode(CodeRequestDto codeRequestDto, Algorithm algorithm) {

        // 테스트케이스 조회
        List<TestcaseResponseDto> testcase = getAllTestcaseByAlgorithmId(algorithm.getAlgorithmId());

        List<ResultDto> results = null;
        if (codeRequestDto.language().equals("java")) {
            results = javaCompiler.runCode(algorithm, testcase, codeRequestDto.detail());
        } else if (codeRequestDto.language().equals("c++")) {
            results = cppCompiler.runCode(algorithm, testcase, codeRequestDto.detail());
        } else if (codeRequestDto.language().equals("python")) {
            results = pythonCompiler.runCode(algorithm, testcase, codeRequestDto.detail());
        }

        return results;
    }

    private List<TestcaseResponseDto> getAllTestcaseByAlgorithmId(Long algorithmId) {
        return testcaseRepository.findAllByAlgorithm_AlgorithmIdAndStatus(algorithmId, Status.ACTIVE)
                .stream().map(TestcaseResponseDto::from).collect(Collectors.toList());
    }
}
