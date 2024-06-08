package goorm.spoco.infra.compiler.controller;

import goorm.spoco.infra.compiler.compiler.JavaCompilerService;
import goorm.spoco.infra.compiler.controller.request.CompileRequest;
import goorm.spoco.infra.compiler.controller.response.CompileResponse;
import goorm.spoco.infra.compiler.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@Controller
public class CompilerController {

    @Autowired
    JavaCompilerService javaCompiler;

    @PostMapping("/api/compiler")
    public ResponseEntity<CompileResponse> compileCode(@RequestBody CompileRequest request) {
        List<Result> results = compile(request.getLanguage(), request.getCode());
        CompileResponse response = new CompileResponse(results);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private List<Result> compile(String language, String code) {
        List<Result> results = null;
        if (language.equals("java")) {
            results = javaCompiler.runCode(code);
        }
        return results;
    }
}
