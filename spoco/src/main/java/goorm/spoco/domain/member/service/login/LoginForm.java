package goorm.spoco.domain.member.service.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginForm {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
