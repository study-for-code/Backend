package goorm.spoco.domain.member.service;

import goorm.spoco.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

//    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
//        return bCryptPasswordEncoder;
//    }
}
