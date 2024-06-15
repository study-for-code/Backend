package goorm.spoco.domain.member.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record MemberModifyDto(

        @Size(min = 3, max = 25)
        @NotEmpty(message = "사용자 ID는 필수항목입니다.")
        String nickname,

        @NotEmpty(message = "비밀번호는 필수항목입니다.")
        String password
) {
}
