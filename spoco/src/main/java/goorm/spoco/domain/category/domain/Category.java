package goorm.spoco.domain.category.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    private String title;
    @Enumerated(EnumType.STRING)
    private CategoryStatus categoryStatus = CategoryStatus.ACTIVE;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "STUDY_ID")
    @JsonIgnore
    private Study study;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Subscribe> subscribes = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void addStudy(Study study) {
        this.study = study;
        study.getCategories().add(this);
    }

    //== 생성 메서드 ==//
    public static Category category(String title, Study study) {
        // categoryCheckMaxValue(study);
        Category category = new Category();
        category.title = title;
        category.addStudy(study);
        return category;
    }

    //== 카테고리 MAX LIMIT 메서드 ==//
    private static void categoryCheckMaxValue(Study study) {
        int maxSize = 10;
        if (study.getCategories().size() >= maxSize) {
            // "카테고리는 " + maxSize + "개 이상 만들 수 없습니다."
        }
    }

    public void delete() {
        this.categoryStatus = CategoryStatus.DELETE;
    }
}
