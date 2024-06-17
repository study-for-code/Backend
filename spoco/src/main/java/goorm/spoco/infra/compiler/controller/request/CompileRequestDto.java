package goorm.spoco.infra.compiler.controller.request;

import lombok.Getter;

@Getter
public class CompileRequestDto {
    private Long algorithmId;
    private String language;
    private String code;
}
