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
@ToString
public class MemberDTO {

    private Long id;

    @Email(message = "유효한 이메일을 입력하세요.")
    @NotEmpty(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotEmpty(message = "사용자 이름은 필수 항목입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    // 이부분 한번 물어보기
    // confirmPassword를 DTO에 넣어야 하는지, 만약 넣어야 한다면 memberController에 DTO를 데이터베이스에 연동하기위해서 세분화 시키는게 맞는지
    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String confirmPassword;
}
