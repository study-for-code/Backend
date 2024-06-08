package goorm.spoco.domain.testcase.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Testcase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TESTCASE_ID")
    private Long testcaseId;

    private String input;
    private String output;

    public Testcase() {
    }
}