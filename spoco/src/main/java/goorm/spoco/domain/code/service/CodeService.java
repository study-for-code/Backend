package goorm.spoco.domain.code.service;


import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.code.controller.request.CodeRequestDto;
import goorm.spoco.domain.code.controller.response.CodeResponseDto;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.code.repository.CodeRepository;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.domain.testcase.controller.response.TestcaseResponseDto;
import goorm.spoco.domain.testcase.domain.Testcase;
import goorm.spoco.domain.testcase.repository.TestcaseRepository;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import goorm.spoco.infra.compiler.compiler.CppCompilerService;
import goorm.spoco.infra.compiler.compiler.JavaCompilerService;
import goorm.spoco.infra.compiler.compiler.PythonCompilerService;
import goorm.spoco.infra.compiler.dto.ResultDto;
import goorm.spoco.infra.compiler.dto.ResultStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;
    private final AlgorithmRepository algorithmRepository;
    private final MemberRepository memberRepository;
    private final TestcaseRepository testcaseRepository;

    private final JavaCompilerService javaCompilerService;
    private final CppCompilerService cppCompilerService;
    private final PythonCompilerService pythonCompilerService;

    /**
     * 코드 제출
     */
    @Transactional
    public CodeResponseDto submit(CodeRequestDto codeRequestDto, Long algorithmId, Long memberId) {
        Member member = existMemberByMemberId(memberId);

        Algorithm algorithm = existAlgorithmByAlgorithmId(algorithmId);

        List<TestcaseResponseDto> testcase = getAllTestcaseByAlgorithmId(algorithmId);

        List<ResultDto> results = null;
        if (codeRequestDto.language().equals("java")) {
            results = javaCompilerService.runCode(algorithm, testcase, codeRequestDto.detail());

        } else if (codeRequestDto.language().equals("c++")) {
            results = cppCompilerService.runCode(algorithm, testcase, codeRequestDto.detail());

        } else if (codeRequestDto.language().equals("python")) {
            results = pythonCompilerService.runCode(algorithm, testcase, codeRequestDto.detail());
        }

        ResultStatus finalStatus = results.stream().map(ResultDto::status)
                .reduce(ResultStatus.PASS,
                        (accStatus, currStatus) -> {
                            if (currStatus.equals(ResultStatus.ERROR)) {
                                return ResultStatus.ERROR;
                            } else if (currStatus.equals(ResultStatus.FAIL) && !accStatus.equals(ResultStatus.ERROR)) {
                                return ResultStatus.FAIL;
                            } else {
                                return accStatus;
                            }
                        });

        if (codeRequestDto.codeId() == null) {
            Code code = codeRepository.save(Code.create(member, algorithm, codeRequestDto.detail(), finalStatus));
            return CodeResponseDto.from(code, results);
        } else {
            Code code = codeRepository.findByCodeIdAndStatus(codeRequestDto.codeId(), Status.ACTIVE)
                    .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 코드를 찾을 수 없습니다."));
            return CodeResponseDto.from(code, results);
        }
    }

    public CodeResponseDto getCodeByAlgorithmId(Long algorithmId, Long memberId) {
        Code code = codeRepository.findByAlgorithm_AlgorithmIdAndMember_MemberIdAndStatus(algorithmId, memberId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 코드를 찾을 수 없습니다."));

        return CodeResponseDto.load(code, null);
    }

    private List<TestcaseResponseDto> getAllTestcaseByAlgorithmId(Long algorithmId) {
        return testcaseRepository.findAllByAlgorithm_AlgorithmIdAndStatus(algorithmId, Status.ACTIVE)
                .stream().map(TestcaseResponseDto::from).collect(Collectors.toList());
    }

    private Algorithm existAlgorithmByAlgorithmId(Long algorithmId) {
        return algorithmRepository.findByAlgorithmIdAndStatus(algorithmId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 알고리즘을 찾을 수 없습니다."));
    }

    private Member existMemberByMemberId(Long memberId) {
        return memberRepository.findByMemberIdAndStatus(memberId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 멤버를 찾을 수 없습니다."));
    }
}
