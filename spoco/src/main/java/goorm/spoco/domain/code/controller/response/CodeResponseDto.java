package goorm.spoco.domain.code.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.infra.compiler.dto.ResultDto;
import goorm.spoco.infra.compiler.dto.ResultStatus;

import java.util.List;

public record CodeResponseDto(
        Long codeId,
        Long memberId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        ResultStatus answerType,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<ResultDto> results

) {
    public static CodeResponseDto from(Code code, List<ResultDto> results) {
        return new CodeResponseDto(
                code.getCodeId(),
                code.getMember().getMemberId(),
                code.getAnswerType(),
                results
        );
    }

    public static CodeResponseDto load(Code code, List<ResultDto> results) {
        return new CodeResponseDto(
                code.getCodeId(),
                code.getMember().getMemberId(),
                null,
                results
        );
    }
}
