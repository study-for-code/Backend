package goorm.spoco.infra.compiler.controller.response;

import goorm.spoco.infra.compiler.dto.Result;
import lombok.Data;

import java.util.List;

@Data
public class CompileResponse {

    private List<Result> results;

    public CompileResponse(List<Result> results) {
        this.results = results;
    }

    public CompileResponse() {
    }
}
