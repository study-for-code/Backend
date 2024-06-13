package goorm.spoco.domain.subscribe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import goorm.spoco.global.status.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSCRIBE_ID")
    private Long subscribeId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    @JsonIgnore
    private Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ALGORITHM_ID")
    @JsonIgnore
    private Algorithm algorithm;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    //== 연관관계 메서드 ==//
    public void addCategory(Category category) {
        this.category = category;
        category.getSubscribes().add(this);
        System.out.println("category subscribe size : " + category.getSubscribes().size());
    }

    public void addAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        algorithm.getSubscribes().add(this);
    }

    //== 생성 메서드 ==//
    public static Subscribe subscribe(Category category, Algorithm algorithm) {
        subscribeValidateDuplicate(category, algorithm);
        Subscribe subscribe = new Subscribe();
        subscribe.addCategory(category);
        subscribe.addAlgorithm(algorithm);
        return subscribe;
    }

    public void delete(Category category, Algorithm algorithm) {
        this.category = category;
        this.algorithm = algorithm;
        category.getSubscribes().remove(algorithm);
        algorithm.getSubscribes().remove(category);
        this.status = Status.DELETE;
    }

    //== 중복 검증 메서드 ==//
    private static void subscribeValidateDuplicate(Category category, Algorithm algorithm) {

        boolean isDuplicate = category.getSubscribes().stream()
                .anyMatch(subscribe -> subscribe.getAlgorithm().getAlgorithmId().equals(algorithm.getAlgorithmId()));

        if (isDuplicate) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 구독되어 있는 알고리즘 입니다.");
        }
    }
}
