package goorm.spoco.domain.member.service;

import goorm.spoco.domain.auth.controller.response.MemberInfoDto;
import goorm.spoco.domain.member.controller.request.MemberModifyDto;
import goorm.spoco.domain.member.controller.request.MemberSignUpDto;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.global.common.auth.SpocoUserDetails;
import goorm.spoco.global.common.response.BaseResponse;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("h2")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원_가입_성공() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("leeho@naver.com", "이호성", "1234", "1234");

        //when
        MemberResponseDto memberResponseDto = memberService.create(memberSignUpDto);

        //then
        assertEquals(memberResponseDto.email(), "leeho@naver.com");
        assertEquals(memberResponseDto.nickname(), "이호성");
    }

    @Test
    public void 비밀번호_확인_실패() throws Exception {

        //when
        CustomException e = assertThrows(CustomException.class, () -> new MemberSignUpDto("leeho@naver.com", "이호성", "1234", "4321"));

        //then
        assertEquals(e.getErrorCode(), ErrorCode.PASSWORD_NOT_MATCH);
    }

    @Test
    public void 이메일_중복_방지() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto1 = new MemberSignUpDto("leeho@naver.com", "이호성", "1234", "1234");
        memberService.create(memberSignUpDto1);

        //when
        MemberSignUpDto memberSignUpDto2 = new MemberSignUpDto("leeho@naver.com", "김태우", "4321", "4321");
        CustomException e = assertThrows(CustomException.class, () -> memberService.create(memberSignUpDto2));

        //then
        assertEquals(e.getErrorCode(), ErrorCode.DUPLICATE_OBJECT);
    }

    @Test
    public void 회원_수정() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("leeho@naver.com", "이호성", "1234", "1234");
        Member member = Member.create(memberSignUpDto);
        MemberInfoDto save = MemberInfoDto.from(memberRepository.save(member));

        SpocoUserDetails userDetails = new SpocoUserDetails(save);
        Authentication authentication = new TestingAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        MemberModifyDto memberModifyDto = new MemberModifyDto("김태우", "4321");
        MemberResponseDto modify = memberService.modify(memberModifyDto, authentication);

        //then
        assertEquals(modify.nickname(), "김태우");
        assertEquals(modify.email(), "leeho@naver.com");
    }

    @Test
    public void 회원_수정_존재하지_않는_멤버() throws Exception {
        //given
        MemberModifyDto memberModifyDto = new MemberModifyDto("김태우", "4321");
        Authentication authentication = new TestingAuthenticationToken(memberModifyDto, "password", "ROLE_MEMBER");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        CustomException e = assertThrows(CustomException.class, () -> memberService.modify(memberModifyDto, authentication));

        //then
        assertEquals(e.getErrorCode(), ErrorCode.RESOURCE_NOT_FOUND);
    }

    @Test
    public void 회원_삭제() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("leeho@naver.com", "이호성", "1234", "1234");
        MemberResponseDto memberResponseDto = memberService.create(memberSignUpDto);

        Authentication authentication = new TestingAuthenticationToken(memberResponseDto, "password", "ROLE_MEMBER");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        Optional<Member> findMember = memberRepository.findByMemberId(memberResponseDto.memberId());
        memberService.delete(memberResponseDto.memberId(), authentication);

        //then
        assertEquals(findMember.get().getStatus(), Status.DELETE);
    }

    @Test
    public void 회원_삭제_존재하지_않는_멤버() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("leeho@naver.com", "이호성", "1234", "1234");
        MemberResponseDto memberResponseDto = memberService.create(memberSignUpDto);

        Authentication authentication = new TestingAuthenticationToken(memberResponseDto, "password", "ROLE_MEMBER");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        CustomException e = assertThrows(CustomException.class, () -> memberService.delete(2L, authentication));

        //then
        assertEquals(e.getErrorCode(), ErrorCode.RESOURCE_NOT_FOUND);
    }

    @Test
    @WithMockUser(username="admin",roles={"ROLE_ADMIN"})
    public void 회원_단일_조회() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("leeho@naver.com", "이호성", "1234", "1234");
        MemberResponseDto memberResponseDto = memberService.create(memberSignUpDto);

        //when
        MemberResponseDto findMember = memberService.getMember(memberResponseDto.memberId());

        //then
        assertEquals(findMember.email(), "leeho@naver.com");
        assertEquals(findMember.nickname(), "이호성");
    }
    
    @Test
    @WithMockUser(username="admin",roles={"ROLE_ADMIN"})
    public void 회원_전체_조회() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto2 = new MemberSignUpDto("namso@naver.com", "이호성", "1234", "1234");
        MemberResponseDto memberResponseDto2 = memberService.create(memberSignUpDto2);

        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("leeho@naver.com", "이호성", "1234", "1234");
        MemberResponseDto memberResponseDto = memberService.create(memberSignUpDto);

        MemberSignUpDto memberSignUpDto1 = new MemberSignUpDto("kimtea@naver.com", "이호성", "1234", "1234");
        MemberResponseDto memberResponseDto1 = memberService.create(memberSignUpDto1);

        MemberSignUpDto memberSignUpDto3 = new MemberSignUpDto("hongjin@naver.com", "이호성", "1234", "1234");
        MemberResponseDto memberResponseDto3 = memberService.create(memberSignUpDto3);

        //when
        List<MemberResponseDto> members = memberService.getAllMembers();

        //then
        assertEquals(members.size(), 4);
        assertEquals(members.get(0).email(), "namso@naver.com");
        assertEquals(members.get(0).nickname(), "이호성");
    }
}