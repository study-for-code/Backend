package goorm.spoco.domain.category.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.spoco.domain.study.domain.Study;
import goorm.spoco.domain.subscribe.domain.Subscribe;
import goorm.spoco.global.common.response.Status;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "STUDY_ID")
    private Study study;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Subscribe> subscribes = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void addStudy(Study study) {
        this.study = study;
        study.getCategories().add(this);
    }

    //== 생성 메서드 ==//
    public static Category create(String title, Study study) {
        categoryCheckMaxValue(study);
        Category category = new Category();
        category.addStudy(study);
        category.title = title;
        category.status = Status.ACTIVE;
        return category;
    }

    //== 카테고리 MAX LIMIT 메서드 ==//
    private static void categoryCheckMaxValue(Study study) {
        int maxSize = 10;
        if (study.getCategories().size() >= maxSize) {
            throw new CustomException(ErrorCode.LIMIT_CATEGORY, "카테고리는 " + maxSize + "개 이상 만들 수 없습니다.");
        }
    }

    public void delete() {
        this.status = Status.DELETE;
        for (Subscribe subscribe : subscribes) {
            subscribe.delete();
        }
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}
