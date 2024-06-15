package goorm.spoco.domain.review.service;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.code.domain.CodeStatus;
import goorm.spoco.domain.code.repository.CodeRepository;
import goorm.spoco.domain.member.controller.request.MemberSignUpDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.member.repository.MemberRepository;
import goorm.spoco.domain.review.controller.response.ReviewResponseDto;
import goorm.spoco.domain.review.domain.ReviewStatus;
import goorm.spoco.domain.review.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("h2")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ReviewServiceTest {

    @Autowired ReviewService reviewService;
    @Autowired MemberRepository memberRepository;
    @Autowired ReviewRepository reviewRepository;

    //test 용
    @Autowired CodeRepository codeRepository;
    @Autowired AlgorithmRepository algorithmRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void 리뷰_생성() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("leeho_@naver.com", "이호성", "1234", "1234");
        Member member1 = Member.create(memberSignUpDto);
        memberRepository.save(member1);

        Algorithm algorithm1 = new Algorithm("제목", "설명");
        algorithmRepository.save(algorithm1);

        Code code = codeRepository.save(Code.code(member1, algorithm1, "풀이", CodeStatus.CLEAR));

        //when
        ReviewResponseDto review = reviewService.createReview(code.getCodeId(), 1);

        //then
        assertEquals(review.codeLine(), 1);
        assertEquals(review.reviewStatus(), ReviewStatus.OPEN.name());
    }

    @Test
    public void 리뷰_삭제() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("leeho_@naver.com", "이호성", "1234", "1234");
        Member member1 = Member.create(memberSignUpDto);
        memberRepository.save(member1);

        Algorithm algorithm1 = new Algorithm("제목", "설명");
        algorithmRepository.save(algorithm1);

        Code code = codeRepository.save(Code.code(member1, algorithm1, "풀이", CodeStatus.CLEAR));

        ReviewResponseDto reviewDto = reviewService.createReview(code.getCodeId(), 1);

        //when
        reviewDto = reviewService.deleteReview(reviewDto.reviewId(), member1.getMemberId());

        //then
        assertEquals(reviewDto.reviewStatus(), ReviewStatus.CLOSE.name());
    }

    @Test
    public void 리뷰_리스트() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("leeho_@naver.com", "이호성", "1234", "1234");
        Member member1 = Member.create(memberSignUpDto);
        memberRepository.save(member1);

        Algorithm algorithm1 = new Algorithm("제목", "설명");
        algorithmRepository.save(algorithm1);

        Code code = codeRepository.save(Code.code(member1, algorithm1, "풀이", CodeStatus.CLEAR));

        for (Integer i = 0; i < 5L; i++) {
            ReviewResponseDto reviewDto = reviewService.createReview(code.getCodeId(), i);
        }

        // when
        List<ReviewResponseDto> reviews = reviewService.getAllByCodeId(code.getCodeId());

        //then
        assertEquals(reviews.size(), 5);
    }
}