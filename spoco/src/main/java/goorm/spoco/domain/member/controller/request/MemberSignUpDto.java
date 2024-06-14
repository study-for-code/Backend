package goorm.spoco.domain.member.controller.request;

import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record MemberSignUpDto(
        @NotEmpty(message = "이메일은 필수항목입니다.")
        @Email
        String email,

        @Size(min = 3, max = 25)
        @NotEmpty(message = "사용자 ID는 필수항목입니다.")
        String nickname,

        @NotEmpty(message = "비밀번호는 필수항목입니다.")
        String password,

        @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
        String confirmPassword
) {
        public MemberSignUpDto {
                if (!password.equals(confirmPassword)) {
                        throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH, "비밀번호가 서로 다릅니다.");
                }
        }
}
