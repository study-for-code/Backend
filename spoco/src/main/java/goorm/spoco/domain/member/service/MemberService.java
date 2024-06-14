package goorm.spoco.domain.member.service;

import goorm.spoco.domain.member.controller.request.MemberModifyDto;
import goorm.spoco.domain.member.controller.request.MemberSignUpDto;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto create(MemberSignUpDto memberSignUpDto) {
        isDuplicateEmail(memberSignUpDto);

        Member member = Member.create(memberSignUpDto);
        return MemberResponseDto.from(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDto modify(Long memberId, MemberModifyDto memberModifyDto) {
        Member member = existsByMemberId(memberId);

        member.updateInfo(memberModifyDto);
        return MemberResponseDto.from(member);
    }

    @Transactional
    public MemberResponseDto delete(Long memberId) {
        Member member = existsByMemberId(memberId);

        member.delete();
        return MemberResponseDto.from(member);
    }

    public MemberResponseDto getMemberByMemberId(Long memberId) {
        return MemberResponseDto.from(existsByMemberId(memberId));
    }

    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll()
                .stream().map(MemberResponseDto::from).collect(Collectors.toList());
    }

    private Member existsByMemberId(Long memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 회원입니다."));
    }

    private void isDuplicateEmail(MemberSignUpDto memberSignUpDto) {
        memberRepository.findByEmail(memberSignUpDto.email())
                .ifPresent(member -> new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 존재하는 이메일입니다."));
    }
}
