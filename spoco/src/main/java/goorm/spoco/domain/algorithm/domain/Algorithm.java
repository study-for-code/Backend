package goorm.spoco.domain.algorithm.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Algorithm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALGORITHM_ID")
    private Long algorithmId;

    private String title;
    private String explanation;

    public Algorithm() {
    }
}
