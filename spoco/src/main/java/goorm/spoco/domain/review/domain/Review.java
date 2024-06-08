package goorm.spoco.domain.review.domain;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.message.domain.Message;
import goorm.spoco.domain.study.domain.Study;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long reviewId;

    private Integer codeLine;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CODE_ID")
    private Code code;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void addCode(Code code) {
        this.code = code;
        code.getReviews().add(this);
    }

    //== 생성 메서드 ==//
    public static Review review(Code code, Integer codeLine) {
        reviewValidateDuplicate(code, codeLine);
        Review review = new Review();
        review.addCode(code);
        review.codeLine = codeLine;
        review.reviewStatus = ReviewStatus.OPEN;
        return review;
    }

    //== 비즈니스 로직 ==//
    public void delete() {
        this.reviewStatus = ReviewStatus.CLOSE;
    }

    //== 중복 검증 메서드 ==//
    private static void reviewValidateDuplicate(Code code, Integer codeLine) {
        if (code.getReviews().stream().anyMatch(review -> review.getCodeLine().equals(codeLine))) {
            // "해당 코드 라인에는 이미 리뷰 생성되어 있습니다."
        }
    }
}
