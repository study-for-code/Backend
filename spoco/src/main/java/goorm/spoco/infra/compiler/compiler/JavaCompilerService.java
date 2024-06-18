package goorm.spoco.infra.compiler.compiler;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.testcase.controller.response.TestcaseResponseDto;
import goorm.spoco.domain.testcase.service.TestcaseService;
import goorm.spoco.global.error.exception.CustomException;
import goorm.spoco.global.error.exception.ErrorCode;
import goorm.spoco.infra.compiler.dto.ResultDto;
import goorm.spoco.infra.compiler.dto.ResultStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JavaCompilerService {

    public List<ResultDto> runCode(Algorithm algorithm, List<TestcaseResponseDto> testcase, String code) {
        List<ResultDto> results = new ArrayList<>();

        for (int i = 0; i < testcase.size(); i++) {
            String input = testcase.get(i).input();
            String expectedOutput = testcase.get(i).output();
            StringBuilder output = new StringBuilder();

            // 임시 파일 생성
            File javaFile = new File("Main.java");
            try (FileWriter writer = new FileWriter(javaFile)) {
                writer.write(code);
            } catch (IOException e) {
                javaFile.delete();
                results.add(ResultDto.builder()
                        .testNum(i+1)
                        .errorMessage("파일 쓰기 에러 : " + e.getMessage())
                        .status(ResultStatus.ERROR).build());
                continue;
            }

            // 컴파일. javaCompiler 경로 맞게 설정해야함.
            String javaCompiler = "javac";
            ProcessBuilder compilePb = new ProcessBuilder(javaCompiler, javaFile.getAbsolutePath());
            try {
                Process compileProcess = compilePb.start();
                compileProcess.waitFor();

                if (compileProcess.exitValue() != 0) {

                    try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()))) {
                        String errorLine;

                        while ((errorLine = errorReader.readLine()) != null) {
                            output.append(errorLine).append("\n");
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                javaFile.delete();
                results.add(ResultDto.builder()
                        .testNum(i+1)
                        .errorMessage("컴파일 에러 : " + e.getMessage())
                        .status(ResultStatus.ERROR).build());
                continue;
            }

            // 자바 파일 실행
            String javaRunner = "java";
            ProcessBuilder javaProcess = new ProcessBuilder(javaRunner, "-Xmx64m", "Main");
            javaProcess.directory(javaFile.getParentFile()); // 클래스 파일이 있는 디렉토리 설정
            try {
                Process runProcess = javaProcess.start();

                // 테스트 케이스 입력 전달
                try (BufferedWriter processInput = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                    String[] testcaseInputs = input.split("\\s+");
                    for (String inputLine : testcaseInputs) {
                        processInput.write(inputLine);
                        processInput.newLine();
                    }
                    processInput.flush();
                }

                // 타이머 시작 및 실행 결과 받아오기
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<String> future = executor.submit(() -> {
                    StringBuilder result = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line).append("\n");
                        }
                    }
                    return result.toString();
                });

                long startTime = System.nanoTime();
                long startMemory = getUsedMemory();

                // 타임아웃 설정 (default 는 테스트케이스마다 2초)
                String result;
                try {
                    result = future.get(algorithm.getTimeLimit(), TimeUnit.SECONDS);

                } catch (TimeoutException e) {
                    runProcess.destroy();
                    future.cancel(true);  // Future 강제 취소
                    results.add(ResultDto.builder()
                            .testNum(i+1)
                            .errorMessage("시간 초과 : : " + e.getMessage())
                            .status(ResultStatus.FAIL).build());
                   continue;

                } catch (ExecutionException e) {
                    if (e.getCause() instanceof OutOfMemoryError) {
                        runProcess.destroy();
                        future.cancel(true);  // Future 강제 취소
                        results.add(ResultDto.builder()
                                .testNum(i+1)
                                .errorMessage("메모리 초과 : : " + e.getMessage())
                                .status(ResultStatus.FAIL).build());
                        continue;

                    } else {
                        runProcess.destroy();
                        results.add(ResultDto.builder()
                                .testNum(i+1)
                                .errorMessage("오류 : : " + e.getMessage())
                                .status(ResultStatus.FAIL).build());
                        continue;
                    }

                } finally {
                    long endTime = System.nanoTime();
                    long endMemory = getUsedMemory();

                    executor.shutdown();
                    javaFile.delete();
                    new File(javaFile.getAbsolutePath().replace(".java", ".class")).delete();

                    long executionTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
                    long usedMemory = endMemory - startMemory;

                    if (usedMemory > algorithm.getMemorySize() * 1024 * 1204) {
                        results.add(ResultDto.builder()
                                .testNum(i+1)
                                .errorMessage("메모리 초과")
                                .status(ResultStatus.FAIL).build());
                    }
                }

                output.append(result);

            } catch (Exception e) {
                output.append("🚨ERROR: ").append(e.getMessage()).append("\n");
            }

            // 통과 여부
            // 아래의 코드는 결과값에 스페이스바가 들어가거나 엔터키가 하나 더 들어가는 등 양식에 조금의 오차가 생기면 FAIL이 되는 문제가 발생함.
            // 양식의 사소한 오차가 있을 때에도 FAIL 로 할 것이라면 주석친 코드를 사용하면 됌.
            boolean isPass = compareOutput(output.toString(), expectedOutput);

            ResultDto result = ResultDto.builder()
                    .testNum(i + 1)
                    .input(input)
                    .expectedResult(expectedOutput)
                    .actualResult(output.toString())
                    .status(isPass ? ResultStatus.PASS : ResultStatus.FAIL)
                    .build();

            results.add(result);
        }
        return results;
    }

    private long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private boolean compareOutput(String actual, String expected) {
        String[] actualTokens = actual.trim().split("\\s+");
        String[] expectedTokens = expected.trim().split("\\s+");
        return Arrays.equals(actualTokens, expectedTokens);
    }
}
