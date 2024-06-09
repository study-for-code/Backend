package goorm.spoco.domain.member.service;

import goorm.spoco.domain.member.controller.response.MemberDTO;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {

        // 1. dto -> entity 객체로 변환
        // 2. repository의 save 메서드 호출
        // repository의 save메서드 호출 ( 조건. entity 객체를 넘겨줘야함 )
        Member member = Member.toMember(memberDTO);
        memberRepository.save(member);

    }
}
