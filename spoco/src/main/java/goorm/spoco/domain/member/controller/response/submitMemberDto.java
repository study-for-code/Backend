package goorm.spoco.domain.member.controller.response;

import goorm.spoco.infra.compiler.dto.ResultStatus;

import javax.xml.transform.Result;

public record submitMemberDto(
        Long subscribeId,
        MemberResponseDto member,
        ResultStatus resultStatus
) {
}
