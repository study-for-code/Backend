package goorm.spoco.domain.code.domain;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.code.controller.request.CodeRequestDto;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.review.domain.Review;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.infra.compiler.dto.ResultDto;
import goorm.spoco.infra.compiler.dto.ResultStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODE_ID")
    private Long codeId;

//    @Lob
    @Column(length = 10000)
    private String detail;

    private Double solveMemory = 0.0;

    private Double solveTime = 0.0;

    private String language;

    @Enumerated(EnumType.STRING)
    private ResultStatus answerType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ALGORITHM_ID")
    private Algorithm algorithm;

    @OneToMany(mappedBy = "code", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();


    //== 연관관계 메서드 ==//
    public void addMember(Member member) {
        this.member = member;
        member.getCodes().add(this);
    }

    public void addAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        algorithm.getCodes().add(this);
    }

    //== 생성 메서드 ==//
    public static Code create(Member member, Algorithm algorithm, CodeRequestDto codeRequestDto, List<ResultDto> results) {
        Code code = new Code();
        code.addMember(member);
        code.addAlgorithm(algorithm);
        code.language = codeRequestDto.language();
        code.detail = codeRequestDto.detail();
        code.solveMemory = getSolveMemory(results);
        code.solveTime = getSolveTime(results);
        code.answerType = getAnswerType(results);

        // 제출 수 증가
        code.getAlgorithm().increaseSubmit();
        if (code.answerType.equals(ResultStatus.PASS)) {
            code.getAlgorithm().increaseAnswer();
        }

        code.status = Status.ACTIVE;
        return code;
    }

    //== 비즈니스 로직 ==//
    public void delete() {
        this.status = Status.DELETE;
    }

    public void update(CodeRequestDto codeRequestDto, List<ResultDto> results){
        this.language = codeRequestDto.language();
        this.detail = codeRequestDto.detail();
        this.solveMemory = getSolveMemory(results);
        this.solveTime = getSolveTime(results);
        this.answerType = getAnswerType(results);

        this.getAlgorithm().increaseSubmit();
        if (this.answerType.equals(ResultStatus.PASS)) {
            this.getAlgorithm().increaseAnswer();
        }
    }

    private static double getSolveMemory(List<ResultDto> results) {
        return results.stream()
                .filter(result -> result.status() != ResultStatus.ERROR && result.status() != ResultStatus.FAIL)
                .mapToDouble(ResultDto::usedMemory).average().orElse(0.0);
    }

    private static double getSolveTime(List<ResultDto> results) {
        return results.stream()
                .filter(result -> result.status() != ResultStatus.ERROR && result.status() != ResultStatus.FAIL)
                .mapToDouble(ResultDto::executionTime).average().orElse(0.0);
    }

    private static ResultStatus getAnswerType(List<ResultDto> results) {
        return results.stream().map(ResultDto::status)
                .reduce(ResultStatus.PASS,
                        (accStatus, currStatus) -> {
                            if (currStatus.equals(ResultStatus.ERROR)) {
                                return ResultStatus.ERROR;
                            } else if (currStatus.equals(ResultStatus.FAIL) && !accStatus.equals(ResultStatus.ERROR)) {
                                return ResultStatus.FAIL;
                            } else {
                                return accStatus;
                            }
                        });
    }


}