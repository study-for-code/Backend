package goorm.spoco.domain.code.service;


import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.code.controller.request.CodeRequestDto;
import goorm.spoco.domain.code.controller.response.CodeResponseDto;
import goorm.spoco.domain.code.repository.CodeRepository;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.domain.testcase.domain.Testcase;
import goorm.spoco.domain.testcase.repository.TestcaseRepository;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import goorm.spoco.infra.compiler.compiler.CppCompilerService;
import goorm.spoco.infra.compiler.compiler.JavaCompilerService;
import goorm.spoco.infra.compiler.compiler.PythonCompilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
//    @Transactional
//    public CodeResponseDto submit(CodeRequestDto codeRequestDto, Long algorithmId, Long memberId) {
//        Member member = memberRepository.findByMemberIdAndStatus(memberId, Status.ACTIVE)
//                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 멤버를 찾을 수 없습니다."));
//
//        Algorithm algorithm = algorithmRepository.findByAlgorithmIdAndStatus(algorithmId, Status.ACTIVE)
//                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 알고리즘을 찾을 수 없습니다."));
//
//        List<Testcase> testcase = testcaseRepository.findAllByAlgorithm_AlgorithmIdAndStatus(algorithmId, Status.ACTIVE);
//
//
//        if (codeRequestDto.language().equals("java")) {
//
//
//        } else if (codeRequestDto.language().equals("c++")) {
//
//
//        } else if (codeRequestDto.language().equals("python")) {
//
//        }
//    }
}
