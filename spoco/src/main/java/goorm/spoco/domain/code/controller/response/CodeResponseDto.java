package goorm.spoco.domain.code.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.infra.compiler.dto.ResultDto;
import goorm.spoco.infra.compiler.dto.ResultStatus;

import java.util.List;

public record CodeResponseDto(
        Long codeId,
        Long memberId,
        String language,
        String detail,
        Double solveMemory,
        Double solveTime,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        ResultStatus answerType,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<ResultDto> results

) {
    public static CodeResponseDto load(Code code) {
        return new CodeResponseDto(
                code.getCodeId(),
                code.getMember().getMemberId(),
                code.getLanguage(),
                code.getDetail(),
                code.getSolveMemory(),
                code.getSolveTime(),
                code.getAnswerType(),
                null
        );
    }

    public static CodeResponseDto submit(Code code, List<ResultDto> results) {
        return new CodeResponseDto(
                code.getCodeId(),
                code.getMember().getMemberId(),
                code.getLanguage(),
                code.getDetail(),
                code.getSolveMemory(),
                code.getSolveTime(),
                code.getAnswerType(),
                results
        );
    }
}
