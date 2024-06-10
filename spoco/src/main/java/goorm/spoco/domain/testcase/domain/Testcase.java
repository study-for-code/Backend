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

    @ManyToOne
    @JoinColumn(name = "ALGORITHM_ID")
    @JsonIgnore
    private Algorithm algorithm;

    public Testcase() {
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
        return testCase;
    }
}