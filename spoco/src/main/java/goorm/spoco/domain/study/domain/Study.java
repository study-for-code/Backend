package goorm.spoco.domain.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Builder
    public Study(String title){
        this.title = title;
        this.createAt = LocalDateTime.now();
    }
}