package goorm.spoco.domain.member.controller.response;

import goorm.spoco.domain.member.domain.Grade;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {

    private Long id;

    @Email(message = "유효한 이메일을 입력하세요.")
    @NotEmpty(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotEmpty(message = "사용자 이름은 필수 항목입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String confirmPassword;

    private Grade grade;
}