package goorm.spoco.domain.study.domain;

import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.domain.member.domain.Member;
import goorm.spoco.domain.study.controller.request.StudyModifyDto;
import goorm.spoco.global.common.response.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    private String joinCode;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member owner;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Join> joins = new ArrayList<>();

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setOwner(Member member) {
        this.owner = member;
    }

    //== 비즈니스 로직 ==//
    public void updateInfo(StudyModifyDto studyModifyDto) {
        this.title = studyModifyDto.title();
    }

    public void delete() {
        this.status = Status.DELETE;
        for (Category category : categories) {
            category.delete();
        }
        for (Join join : joins) {
            join.delete();
        }
    }

    //== 생성 메서드 ==//
    public static Study create(String title, Member owner, String joinCode) {
        Study study = new Study();
        study.title = title;
        study.setOwner(owner);
        study.createAt = LocalDateTime.now();
        study.joinCode = joinCode;
        study.status = Status.ACTIVE;
        return study;
    }
}