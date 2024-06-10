package goorm.spoco.domain.algorithm.domain;

import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import goorm.spoco.domain.testcase.domain.Testcase;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Algorithm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALGORITHM_ID")
    private Long algorithmId;

    private String title;
    private String explanation;
    private AlgorithmStatus algorithmStatus;

    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<Subscribe> subscribes = new ArrayList<>();

    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<Testcase> testCases = new ArrayList<>();

    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<Code> codes = new ArrayList<>();

    public Algorithm() {
        this.algorithmStatus = AlgorithmStatus.ACTIVE;
    }

    //== 생성 메서드 ==//
    // 해당 매개변수는 request 객체로 변경
    public static Algorithm algorithm(Algorithm algorithmRequest) {
        Algorithm algorithm = new Algorithm();
        algorithm.title = algorithmRequest.title;
        algorithm.explanation = algorithmRequest.explanation;
        algorithm.algorithmStatus = algorithmRequest.algorithmStatus;
        return algorithm;
    }

    // 테스트 용 생성자
    public Algorithm(String title, String explanation) {
        this.title = title;
        this.explanation = explanation;
        this.algorithmStatus = AlgorithmStatus.ACTIVE;
    }

    public void delete() {
        this.algorithmStatus = AlgorithmStatus.DELETE;
    }

}
