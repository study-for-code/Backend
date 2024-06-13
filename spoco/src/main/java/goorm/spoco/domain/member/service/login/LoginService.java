package goorm.spoco.domain.member.service.login;

import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    /**
     *
     @return null이면 로그인 실패
     */
    public Member login(Long memberId, String password) {
        return memberRepository.findById(memberId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
