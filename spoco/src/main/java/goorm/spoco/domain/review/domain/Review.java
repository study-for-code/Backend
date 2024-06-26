package goorm.spoco.domain.review.domain;

import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CODE_ID")
    private Code code;

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
        review.status = Status.ACTIVE;
        return review;
    }

    //== 비즈니스 로직 ==//
    public void delete() {
        this.status = Status.DELETE;
    }

    //== 중복 검증 메서드 ==//
    private static void reviewValidateDuplicate(Code code, Integer codeLine) {
        if (code.getReviews().stream().anyMatch(review -> review.getCodeLine().equals(codeLine))) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "해당 코드 라인에 리뷰가 존재합니다.");
        }
    }
}
