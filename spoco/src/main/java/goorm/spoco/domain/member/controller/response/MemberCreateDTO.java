package goorm.spoco.domain.member.controller.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateDTO {
    private String memberEmail;
    private String password;

    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String passwordA;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String passwordB;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;
}
