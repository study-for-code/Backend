package goorm.spoco.domain.testcase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.testcase.controller.request.TestcaseRequestDto;
import goorm.spoco.global.common.response.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Getter
@Entity
public class Testcase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TESTCASE_ID")
    private Long testcaseId;

    @Column(length = 10000)
    private String input;

    @Column(length = 10000)
    private String output;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "ALGORITHM_ID")
    private Algorithm algorithm;

    //== 연관관계 메서드 ==//
    public void addAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        algorithm.getTestCases().add(this);
    }

    //== 생성 메서드 ==//
    public static Testcase create(TestcaseRequestDto testcaseRequestDto, Algorithm algorithm) {
        Testcase testCase = new Testcase();
        testCase.input = testcaseRequestDto.input();
        testCase.output = testcaseRequestDto.output();
        testCase.addAlgorithm(algorithm);
        testCase.status = Status.ACTIVE;
        return testCase;
    }

    public void delete() {
        this.status = Status.DELETE;
    }

    public void updateInfo(TestcaseRequestDto testcaseRequestDto) {
        this.input = testcaseRequestDto.input();
        this.output = testcaseRequestDto.output();
    }
}