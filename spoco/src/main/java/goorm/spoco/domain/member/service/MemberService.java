package goorm.spoco.domain.member.service;

import goorm.spoco.domain.member.controller.response.MemberDTO;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@Getter
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDTO create(String nickname, String email, String password) {
        // 생성을 Create 로 사용해도 되는지
        MemberDTO user = new MemberDTO();
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
//        this.memberRepository.save(user); // => 이부분 어떻게 처리 해야되는지 여쭤보기
        return user;
    }
}
