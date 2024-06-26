package goorm.spoco.domain.member.domain;

import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.member.controller.request.MemberModifyDto;
import goorm.spoco.domain.member.controller.request.MemberSignUpDto;
import goorm.spoco.global.common.response.Status;
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
    private Long memberId;

    private String email;

    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Join> joins = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Code> codes = new ArrayList<>();

    //== 생성 메서드 ==//
    public static Member create(MemberSignUpDto memberSignUpDto) {
        Member member = new Member();
        member.email = memberSignUpDto.email();
        member.nickname = memberSignUpDto.nickname();
        member.password = memberSignUpDto.password();
        if (memberSignUpDto.nickname().equals("admin")) {
            member.role = Role.ADMIN;
        } else {
            member.role = Role.MEMBER;
        }
        member.status = Status.ACTIVE;
        return member;
    }


    //== 비즈니스 메소드 ==//
    public void updateInfo(MemberModifyDto memberModifyDto) {
        this.nickname = memberModifyDto.nickname();
        this.password = memberModifyDto.password();
    }

    public void encoder(String password) {
        this.password = password;
    }

    public void delete() {
        this.status = Status.DELETE;
        for (Join join : joins) {
            join.delete();
        }
        for (Code code : codes) {
            code.delete();
        }
    }

}
