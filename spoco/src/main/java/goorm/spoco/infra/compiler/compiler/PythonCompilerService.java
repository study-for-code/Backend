package goorm.spoco.infra.compiler.compiler;

import goorm.spoco.domain.testcase.repository.TestcaseRepository;
import goorm.spoco.infra.compiler.dto.Result;
import goorm.spoco.infra.compiler.dto.ResultStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PythonCompilerService {

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
                File pythonFile = new File("Main.py");
                try (FileWriter writer = new FileWriter(pythonFile)) {
                    writer.write(code);
                }

                // 실행 파일 실행
                String pythonInterpreter = "python3";
                ProcessBuilder compilePb = new ProcessBuilder(pythonInterpreter, pythonFile.getAbsolutePath());
                Process runProcess = compilePb.start();

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

                // 문법적 오류 발생시 에러 및 종료
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                StringBuilder errorOutput = new StringBuilder();
                while ((line = errorReader.readLine()) != null) {
                    errorOutput.append("ERROR: ").append(line).append("\n");
                }
                if (errorOutput.length() > 0) {
                    results.add(new Result(errorOutput.toString(), ResultStatus.ERROR));
                    return results;
                }


                pythonFile.delete();

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