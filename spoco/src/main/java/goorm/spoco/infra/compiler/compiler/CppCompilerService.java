package goorm.spoco.infra.compiler.compiler;

import goorm.spoco.domain.testcase.repository.TestcaseRepository;
import goorm.spoco.infra.compiler.dto.Result;
import goorm.spoco.infra.compiler.dto.ResultStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CppCompilerService {

    private final TestcaseRepository testcaseRepository;

    public List<Result> runCode(String code) {
        List<Result> results = new ArrayList<>();
        List<String> inputs = testcaseRepository.findAllInput();
        List<String> outputs = testcaseRepository.findAllOutput();

        for (int i = 0; i < inputs.size(); i++) {
            String testcase = inputs.get(i);
            String expectedOutput = outputs.get(i);
            StringBuilder output = new StringBuilder();

            try {
                // 임시 파일 생성
                File cppFile = new File("Main.cpp");
                try (FileWriter writer = new FileWriter(cppFile)) {
                    writer.write(code);
                }

                // 컴파일 및 Main 파일 생성
                String cppCompiler = "/usr/bin/g++";
                ProcessBuilder compilePb = new ProcessBuilder(cppCompiler, cppFile.getAbsolutePath(), "-o", "Main");
                Process compileProcess = compilePb.start();
                compileProcess.waitFor();

                // 컴파일에러 발생 시 에러 처리
                if (compileProcess.exitValue() != 0) {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        output.append(errorLine).append("\n");
                    }
                    results.add(new Result(i + 1, inputs.get(i), outputs.get(i), "ERROR", ResultStatus.ERROR));
                    cppFile.delete();
                    continue;
                }

                // 실행 파일 실행
                ProcessBuilder runPb = new ProcessBuilder("./Main");
                runPb.directory(cppFile.getParentFile()); // 실행 파일이 있는 디렉토리 설정
                Process runProcess = runPb.start();

                // 테스트 케이스 입력 전달
                try (BufferedWriter processInput = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                    String[] testcaseInputs = testcase.split("\\s+");
                    for (String inputLine : testcaseInputs) {
                        processInput.write(inputLine);
                        processInput.newLine();
                    }
                    processInput.flush();
                }

                // 실행 결과 받아오기
                BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                cppFile.delete();
                new File(cppFile.getAbsolutePath().replace(".cpp", "")).delete();

            } catch (Exception e) {
                output.append("🚨ERROR : ").append(e.getMessage()).append("\n");
            }

            // 통과 여부
            boolean isPass = output.toString().trim().equals(expectedOutput.trim());

            Result result = Result.builder()
                    .testNum(i + 1)
                    .input(testcase)
                    .expectedResult(expectedOutput)
                    .actualResult(output.toString())
                    .status(isPass ? ResultStatus.PASS : ResultStatus.FAIL)
                    .build();

            results.add(result);
        }
        return results;
    }
}

