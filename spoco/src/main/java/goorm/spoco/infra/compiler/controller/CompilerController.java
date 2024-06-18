package goorm.spoco.infra.compiler.controller;

import goorm.spoco.global.common.response.BaseResponse;
import goorm.spoco.infra.compiler.compiler.CppCompilerService;
import goorm.spoco.infra.compiler.compiler.JavaCompilerService;
import goorm.spoco.infra.compiler.compiler.PythonCompilerService;
import goorm.spoco.infra.compiler.controller.request.CompileRequestDto;
import goorm.spoco.infra.compiler.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CompilerController {

    private final JavaCompilerService javaCompiler;
    private final CppCompilerService cppCompiler;
    private final PythonCompilerService pythonCompiler;

//    @PostMapping("/api/compiler")
//    public ResponseEntity<CompileResponse> compileCode(@RequestBody CompileRequest request) {
//        List<Result> results = compile(request.getLanguage(), request.getCode());
//        CompileResponse response = new CompileResponse(results);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    @PostMapping("/compiler")
    public BaseResponse compileCode(@RequestBody CompileRequestDto request) {
        return BaseResponse.<ResultDto>builder()
                .message("컴파일 실행")
                .build();
    }
}