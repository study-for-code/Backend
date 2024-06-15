package goorm.spoco.domain.member.service;

import goorm.spoco.domain.member.controller.request.MemberModifyDto;
import goorm.spoco.domain.member.controller.request.MemberSignUpDto;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.domain.Role;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.global.common.auth.SpocoUserDetails;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public MemberResponseDto create(MemberSignUpDto memberSignUpDto) {
        isDuplicateEmail(memberSignUpDto);

        Member member = memberRepository.save(Member.create(memberSignUpDto));
        member.encoder(encoder.encode(member.getPassword()));

        return MemberResponseDto.from(member);
    }

    @Transactional
    public MemberResponseDto modify(MemberModifyDto memberModifyDto, Authentication authentication) {
        Member member = existsByAuthentication(authentication);

        member.updateInfo(memberModifyDto);
        return MemberResponseDto.from(member);
    }

    @Transactional
    public MemberResponseDto delete(Long memberId, Authentication authentication) {

        Member current = existsByAuthentication(authentication);
        Member resource = existsByMemberId(memberId);

        if (current.getMemberId().equals(resource.getMemberId())
                    || current.getRole().equals(Role.ADMIN)) {
            resource.delete();
        }

        return MemberResponseDto.from(resource);
    }

    public MemberResponseDto getMember(Long memberId) {
        Member member = existsByMemberId(memberId);
        return MemberResponseDto.from(member);
    }

    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll()
                .stream().map(MemberResponseDto::from).collect(Collectors.toList());
    }

    private Member existsByAuthentication(Authentication authentication) {
        SpocoUserDetails userDetails = (SpocoUserDetails) authentication.getDetails();
        return existsByMemberId(Long.parseLong(userDetails.getUsername()));
    }

    private Member existsByMemberId(Long memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 회원입니다."));
    }

    private void isDuplicateEmail(MemberSignUpDto memberSignUpDto) {
        memberRepository.findByEmail(memberSignUpDto.email())
                .ifPresent(member -> { throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 존재하는 이메일입니다."); });
    }
}
