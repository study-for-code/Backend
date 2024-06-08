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

    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<Subscribe> subscribes = new ArrayList<>();

    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<Testcase> testCases = new ArrayList<>();

    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<Code> codes = new ArrayList<>();

    public Algorithm() {
    }

    //== 생성 메서드 ==//
    // 해당 매개변수는 request 객체로 변경
    public static Algorithm algorithm(Algorithm algorithmRequest) {
        Algorithm algorithm = new Algorithm();
        algorithm.title = algorithmRequest.title;
        algorithm.explanation = algorithmRequest.explanation;
        return algorithm;
    }
}
