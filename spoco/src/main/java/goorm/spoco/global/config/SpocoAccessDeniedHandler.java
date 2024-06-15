package goorm.spoco.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import goorm.spoco.global.common.response.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "FORBIDDEN_EXCEPTION_HANDLER")
@Component
@RequiredArgsConstructor
public class SpocoAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    // 특정 리소스에 대한 권한이 없을 경우(인가) 호출
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("No Authorities: {}", accessDeniedException);

        // 클라이언트에게 반환할 응답 메시지 구성

        // 응답의 Content-Type 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");

        // 응답 메시지를 JSON 형식으로 변환하여 클라이언트에게 전송
        objectMapper.writeValue(response.getOutputStream(), BaseResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .httpStatus(HttpStatus.FORBIDDEN)
                .message(accessDeniedException.getMessage())
                .build()
        );
    }
}
