package goorm.spoco.domain.algorithm.dto;

import lombok.Data;

@Data
public class AlgorithmDTO {
    private String title;
    private String explanation;

    public AlgorithmDTO(String title, String explanation) {
        this.title = title;
        this.explanation = explanation;
    }
}
