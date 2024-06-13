package goorm.spoco.infra.compiler.controller.request;

import lombok.Data;

@Data
public class CompileRequest {
    private String language;
    private String code;
}
