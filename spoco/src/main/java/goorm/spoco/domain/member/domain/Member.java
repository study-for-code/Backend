package goorm.spoco.domain.member.domain;

import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.member.controller.response.MemberDTO;
import goorm.spoco.domain.message.domain.Message;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Join> joins = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Code> codes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

// 차후에 물어볼 사안
    public static Member toMember(MemberDTO memberDTO) {
        Member member = new Member();
        member.email = memberDTO.getEmail();
        member.nickname = memberDTO.getNickname();
        member.password =  memberDTO.getPassword();
        member.grade = Grade.MEMBER;
        return member;
    }

    //== 생성 메서드 ==//
    // 해당 매개변수는 request 객체도 변경
    public static Member member(Member signUpRequest) {
        Member member = new Member();
        member.email = signUpRequest.email;
        member.nickname = signUpRequest.nickname;
        // 암호화해서 DB에 저장
        member.password = signUpRequest.password;
        member.grade = Grade.MEMBER;
        return member;
    }
}
