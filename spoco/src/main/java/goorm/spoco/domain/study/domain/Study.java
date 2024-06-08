package goorm.spoco.domain.study.domain;

import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.join.domain.Join;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDY_ID")
    private Long studyId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Join> joins = new ArrayList<>();

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    //== 생성 메서드 ==//
    public static Study study(String title) {
        Study study = new Study();
        study.title = title;
        study.createAt = LocalDateTime.now();
        return study;
    }
}