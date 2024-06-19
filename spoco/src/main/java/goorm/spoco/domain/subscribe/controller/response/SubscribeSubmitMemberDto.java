package goorm.spoco.domain.subscribe.controller.response;

import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.infra.compiler.dto.ResultStatus;

public record SubscribeSubmitMemberDto(
        Long subscribeId,

        MemberResponseDto member,

        ResultStatus status
) {
}
