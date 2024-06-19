package goorm.spoco.domain.code.service;


import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.code.controller.request.CodeRequestDto;
import goorm.spoco.domain.code.controller.response.CodeResponseDto;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.code.repository.CodeRepository;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import goorm.spoco.infra.compiler.dto.ResultDto;
import goorm.spoco.infra.compiler.service.CompilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;
    private final AlgorithmRepository algorithmRepository;
    private final MemberRepository memberRepository;

    private final CompilerService compilerService;


    /**
     * 코드 제출
     */
    @Transactional
    public CodeResponseDto submit(CodeRequestDto codeRequestDto, Long memberId) {
        // 멤버 조회
        Member member = existMemberByMemberId(memberId);
        // 알고리즘 조회
        Algorithm algorithm = existAlgorithmByAlgorithmId(codeRequestDto.algorithmId());

        List<ResultDto> results = compilerService.submitCode(codeRequestDto, algorithm);

        Code code = codeRepository.findByAlgorithm_AlgorithmIdAndMember_MemberIdAndStatus(codeRequestDto.algorithmId(), memberId, Status.ACTIVE)
                .map(existingCode -> {
                    existingCode.update(codeRequestDto, results);
                    return existingCode;
                }).orElseGet(() -> {
                    Code newCode = Code.create(member, algorithm, codeRequestDto, results);
                    return codeRepository.save(newCode);
                });

        return CodeResponseDto.submit(code, results);
    }

//    @Transactional
//    public CodeResponseDto modify(CodeRequestDto codeRequestDto, Long codeId) {
//        // 알고리즘 조회
//        Algorithm algorithm = existAlgorithmByAlgorithmId(codeRequestDto.algorithmId());
//
//        ResultListDto results = compilerService.submitCode(codeRequestDto, algorithm);
//
//
//        return CodeResponseDto.submit(code, results);
//    }


    public CodeResponseDto getCode(Long algorithmId, Long memberId) {

        Code code = codeRepository.findByAlgorithm_AlgorithmIdAndMember_MemberIdAndStatus(algorithmId, memberId, Status.ACTIVE)
                    .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 코드를 찾을 수 없습니다."));

        return CodeResponseDto.load(code);
    }

    private Algorithm existAlgorithmByAlgorithmId(Long algorithmId) {
        return algorithmRepository.findByAlgorithmIdAndStatus(algorithmId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 알고리즘을 찾을 수 없습니다."));
    }

    private Member existMemberByMemberId(Long memberId) {
        return memberRepository.findByMemberIdAndStatus(memberId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 멤버를 찾을 수 없습니다."));
    }
    private Code existCodeByCodeId(Long codeId) {
        return codeRepository.findByCodeIdAndStatus(codeId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "해당 코드를 찾을 수 없습니다."));
    }
}
