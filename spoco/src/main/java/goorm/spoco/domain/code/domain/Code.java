package goorm.spoco.domain.code.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODE_ID")
    private Long codeId;

    private String detail;
    @Column(name = "CODE_STATUS")
    private CodeStatus codeStatus = CodeStatus.NONE;

    public Code() {
    }
}