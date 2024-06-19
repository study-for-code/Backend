package goorm.spoco.domain.testcase.dto;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class TestcaseDTO {

    private String input;

    private String output;

    public TestcaseDTO() {
    }

    public TestcaseDTO(String input, String output) {
        this.input = input;
        this.output = output;
    }
}
