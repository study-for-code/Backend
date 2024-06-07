package goorm.spoco.domain.testcase.domain;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TestCase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TESTCASE_ID")
    private Long testcaseId;

    private String input;
    private String output;

    @ManyToOne
    @JoinColumn(name = "ALGORITHM_ID")
    private Algorithm algorithm;

    public TestCase() {
    }

    //== 연관관계 메서드 ==//
    public void addAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        algorithm.getTestCases().add(this);
    }

    //== 생성 메서드 ==//
    public static TestCase testCase(TestCase testCaseRequest) {
        TestCase testCase = new TestCase();
        testCase.input = testCaseRequest.input;
        testCase.output = testCaseRequest.output;
        return testCase;
    }
}