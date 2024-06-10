package goorm.spoco.domain.testcase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.spoco.domain.algorithm.domain.Algorithm;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Testcase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TESTCASE_ID")
    private Long testcaseId;

    private String input;
    private String output;

    @Enumerated(EnumType.STRING)
    private TestcaseStatus testcaseStatus;

    @ManyToOne
    @JoinColumn(name = "ALGORITHM_ID")
    @JsonIgnore
    private Algorithm algorithm;

    public Testcase() {
        this.testcaseStatus = TestcaseStatus.ACTIVE;
    }

    //== 연관관계 메서드 ==//
    public void addAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        algorithm.getTestCases().add(this);
    }

    //== 생성 메서드 ==//
    public static Testcase testCase(Testcase testCaseRequest) {
        Testcase testCase = new Testcase();
        testCase.input = testCaseRequest.input;
        testCase.output = testCaseRequest.output;
        testCase.testcaseStatus = TestcaseStatus.ACTIVE;
        return testCase;
    }

    public void delete() {
        this.testcaseStatus = TestcaseStatus.DELETE;
    }

    //== 복구 기능 ==//
//    public void restore() {
//        this.testcaseStatus = TestcaseStatus.ACTIVE;
//    }
}