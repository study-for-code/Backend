package goorm.spoco.domain.algorithm.domain;

import goorm.spoco.domain.algorithm.controller.request.AlgorithmRequestDto;
import goorm.spoco.domain.algorithm.repository.AlgorithmRepository;
import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import goorm.spoco.domain.testcase.domain.Testcase;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.util.StringListConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Algorithm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALGORITHM_ID")
    private Long algorithmId;

    private String title;

    @Convert(converter = StringListConverter.class)
    private List<String> restrictions;

    private Integer memorySize;

    private Integer timeLimit;

    private Integer submit;

    private Integer answer;

    private Double answerRate;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<Subscribe> subscribes = new ArrayList<>();

    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<Testcase> testCases = new ArrayList<>();

    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<Code> codes = new ArrayList<>();

    public Algorithm() {
        this.status = Status.ACTIVE;
    }

    //== 생성 메서드 ==//
    // 해당 매개변수는 request 객체로 변경
    public static Algorithm create(AlgorithmRequestDto algorithmRequestDto) {
        Algorithm algorithm = new Algorithm();
        algorithm.title = algorithmRequestDto.title();
        algorithm.explanation = algorithmRequestDto.explanation();
        algorithm.restrictions = algorithmRequestDto.restrictions();
        algorithm.memorySize = algorithmRequestDto.memorySize();
        algorithm.timeLimit = algorithmRequestDto.timeLimit();
        algorithm.submit = 0;
        algorithm.answer = 0;
        algorithm.answerRate = 0.0;
        algorithm.status = Status.ACTIVE;
        return algorithm;
    }

    //== 비즈니스 로직 ==//
    public void updateInfo(AlgorithmRequestDto algorithmRequestDto) {
        this.title = this.algorithmId + "-" + algorithmRequestDto.title();
        this.explanation = algorithmRequestDto.explanation();
        this.restrictions = algorithmRequestDto.restrictions();
        this.memorySize = algorithmRequestDto.memorySize();
        this.timeLimit = algorithmRequestDto.timeLimit();
    }

    public void updateTitle(Algorithm algorithm) {
        this.title = algorithm.getAlgorithmId() + "-" + algorithm.getTitle();
    }

    public void delete() {
        this.status = Status.DELETE;
        for (Subscribe subscribe : subscribes) {
            subscribe.delete();
        }
        for (Testcase testcase : testCases) {
            testcase.delete();
        }
        for (Code code : codes) {
            code.delete();
        }
    }

    // 제출 수 증가
    public void increaseSubmit() {
        this.submit += 1;
    }

    public void increaseAnswer() {
        this.answer += 1;
    }

    public Double getAnswerRate() {
        if (!this.submit.equals(0)) {
            this.answerRate = (double) this.answer / this.submit;
        } else {
            this.answerRate = 0.0;
        }
        return this.answerRate;
    }

}
