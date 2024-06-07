package goorm.spoco.domain.member.domain;

import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.message.domain.Message;
import goorm.spoco.domain.study.domain.Study;
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
    private Grade grade;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Join> joins = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Code> codes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

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
