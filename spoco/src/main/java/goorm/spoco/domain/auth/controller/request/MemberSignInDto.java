package goorm.spoco.domain.auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record MemberSignInDto(
        @Email
        @NotEmpty(message = "이메일은 필수항목입니다.")
        String email,

        @NotEmpty(message = "비밀번호는 필수항목입니다.")
        String password
) {
}
