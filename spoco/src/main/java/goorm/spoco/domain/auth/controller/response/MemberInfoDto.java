package goorm.spoco.domain.auth.controller.response;

import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.domain.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String password;
    private Role role;

    public static MemberInfoDto from(Member member) {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.memberId = member.getMemberId();
        memberInfoDto.email = member.getEmail();
        memberInfoDto.nickname = member.getNickname();
        memberInfoDto.password = member.getPassword();
        memberInfoDto.role = member.getRole();
        return memberInfoDto;
    }
}
