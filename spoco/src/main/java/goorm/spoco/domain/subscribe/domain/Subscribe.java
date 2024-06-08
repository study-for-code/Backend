package goorm.spoco.domain.subscribe.domain;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.study.domain.Study;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSCRIBE_ID")
    private Long subscribeId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ALGORITHM_ID")
    private Algorithm algorithm;

    //== 연관관계 메서드 ==//
    public void addCategory(Category category) {
        this.category = category;
        category.getSubscribes().add(this);
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

    //== 중복 검증 메서드 ==//
    private static void subscribeValidateDuplicate(Category category, Algorithm algorithm) {
        if (category.getSubscribes().stream().anyMatch(subscribe -> subscribe.getAlgorithm().equals(algorithm))) {
            // "카테고리에 이미 해당 알고리즘이 구독되어 있습니다."
        }
    }
}
