package goorm.spoco.domain.member.service;

import goorm.spoco.domain.member.controller.request.MemberModifyDto;
import goorm.spoco.domain.member.controller.request.MemberSignUpDto;
import goorm.spoco.domain.member.controller.response.MemberResponseDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.global.common.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        MemberResponseDto memberResponseDto = memberService.create(memberSignUpDto);

        //when
        MemberModifyDto memberModifyDto = new MemberModifyDto("김태우", "4321");
        MemberResponseDto modify = memberService.modify(memberResponseDto.memberId(), memberModifyDto);

        //then
        assertEquals(modify.nickname(), "김태우");
        assertEquals(modify.email(), "leeho@naver.com");
        assertEquals(modify.password(), "4321");
    }

    @Test
    public void 회원_수정_존재하지_않는_멤버() throws Exception {
        //given
        MemberModifyDto memberModifyDto = new MemberModifyDto("김태우", "4321");

        //when
        CustomException e = assertThrows(CustomException.class, () -> memberService.modify(1L, memberModifyDto));

        //then
        assertEquals(e.getErrorCode(), ErrorCode.RESOURCE_NOT_FOUND);
    }

    @Test
    public void 회원_삭제() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("leeho@naver.com", "이호성", "1234", "1234");
        MemberResponseDto memberResponseDto = memberService.create(memberSignUpDto);

        //when
        Optional<Member> findMember = memberRepository.findByMemberId(memberResponseDto.memberId());
        memberService.delete(memberResponseDto.memberId());

        //then
        assertEquals(findMember.get().getStatus(), Status.DELETE);
    }

    @Test
    public void 회원_삭제_존재하지_않는_멤버() throws Exception {
        //given

        //when
        CustomException e = assertThrows(CustomException.class, () -> memberService.delete(1L));

        //then
        assertEquals(e.getErrorCode(), ErrorCode.RESOURCE_NOT_FOUND);
    }

    @Test
    public void 회원_단일_조회() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("leeho@naver.com", "이호성", "1234", "1234");
        MemberResponseDto memberResponseDto = memberService.create(memberSignUpDto);

        //when
        MemberResponseDto findMember = memberService.getMemberByMemberId(memberResponseDto.memberId());

        //then
        assertEquals(findMember.email(), "leeho@naver.com");
        assertEquals(findMember.nickname(), "이호성");
        assertEquals(findMember.password(), "1234");
    }
    
    @Test
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
        assertEquals(members.get(0).password(), "1234");
    }
}