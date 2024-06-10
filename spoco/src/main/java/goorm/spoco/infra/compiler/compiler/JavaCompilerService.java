package goorm.spoco.infra.compiler.compiler;

import goorm.spoco.domain.testcase.repository.TestcaseRepository;
import goorm.spoco.infra.compiler.dto.Result;
import goorm.spoco.infra.compiler.dto.ResultStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JavaCompilerService {

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
                File javaFile = new File("Main.java");
                try (FileWriter writer = new FileWriter(javaFile)) {
                    writer.write(code);
                }

                // 컴파일. javaCompiler 경로 맞게 설정해야함.
                String javaCompiler = "javac";
                ProcessBuilder compilePb = new ProcessBuilder(javaCompiler, javaFile.getAbsolutePath());
                Process compileProcess = compilePb.start();
                compileProcess.waitFor();

                // 컴파일에러 발생 시 에러 및 종료
                if (compileProcess.exitValue() != 0) {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        output.append(errorLine).append("\n");
                    }
                    results.add(new Result(output.toString(), ResultStatus.ERROR));
                    javaFile.delete();
                    return results;
                }

                // 자바 파일 실행
                String javaRunner = "java";
                ProcessBuilder javaProcess = new ProcessBuilder(javaRunner, "Main");
                javaProcess.directory(javaFile.getParentFile()); // 클래스 파일이 있는 디렉토리 설정
                Process runProcess = javaProcess.start();

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

//                // 실행 중 에러 읽기, 에러 코드가 필요할 경우 사용
//                BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
//                String errorLine;
//                while ((errorLine = errorReader.readLine()) != null) {
//                    output.append(errorLine).append("\n");
//                }

                javaFile.delete();
                new File(javaFile.getAbsolutePath().replace(".java", ".class")).delete();

            } catch (Exception e) {
                output.append("🚨ERROR : ").append(e.getMessage()).append("\n");
            }

            // 통과 여부
            // 아래의 코드는 결과값에 스페이스바가 들어가거나 엔터키가 하나 더 들어가는 등 양식에 조금의 오차가 생기면 FAIL이 되는 문제가 발생함.
            // 양식의 사소한 오차가 있을 때에도 FAIL 로 할 것이라면 주석친 코드를 사용하면 됌.
//            boolean isPass = output.toString().trim().equals(expectedOutput.trim());
            boolean isPass = compareOutput(output.toString(), expectedOutput);

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

    private boolean compareOutput(String actual, String expected) {
        String[] actualTokens = actual.trim().split("\\s+");
        String[] expectedTokens = expected.trim().split("\\s+");
        return Arrays.equals(actualTokens, expectedTokens);
    }
}
