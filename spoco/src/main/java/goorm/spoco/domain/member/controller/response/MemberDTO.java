package goorm.spoco.domain.member.controller.response;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
//@ToString
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' + '}';
    }
}
