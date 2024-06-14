package goorm.spoco.domain.member.domain;

import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.member.controller.request.MemberModifyDto;
import goorm.spoco.domain.member.controller.request.MemberRequestDto;
import goorm.spoco.domain.member.controller.request.MemberSignUpDto;
import goorm.spoco.global.common.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import jakarta.persistence.*;
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
    private Long Id;

    private String email;

    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Join> joins = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Code> codes = new ArrayList<>();

    //== 생성 메서드 ==//
    public static Member create(MemberSignUpDto memberSignUpDto) {
        confirmPassword(memberSignUpDto.password(), memberSignUpDto.confirmPassword());

        Member member = new Member();
        member.email = memberSignUpDto.email();
        member.nickname = memberSignUpDto.nickname();
        member.password = memberSignUpDto.password();
        member.grade = Grade.MEMBER;
        member.status = Status.ACTIVE;
        return member;
    }

    private static void confirmPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH, "비밀번호가 서로 다릅니다.");
        }
    }

    //== 비즈니스 메소드 ==//
    public void updateInfo(MemberModifyDto memberModifyDto) {
        this.nickname = memberModifyDto.nickname();
        this.password = memberModifyDto.password();
    }

    public void delete() {
        this.status = Status.DELETE;
        for (Join join : this.getJoins()) {
            join.delete();
        }
        for (Code code : this.getCodes()) {
            code.delete();
        }
    }

}
