package goorm.spoco.domain.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Email(message = "유효한 이메일을 입력하세요.")
    @NotEmpty(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotEmpty(message = "사용자 이름은 필수 항목입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Grade grade;
}
