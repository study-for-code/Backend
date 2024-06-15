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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j(topic = "UNAUTHORIZATION_EXCEPTION_HANDLER")
@Component
@RequiredArgsConstructor
public class SpocoAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    //  인증되지 않은 요청이 들어왔을 때 클라이언트에게 적절한 오류 응답을 제공하는 엔트리 포인트를 구현
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.error("Unauthorized error: {}", authException.getMessage());

        // 응답의 Content-Type 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");


        // 응답 메시지를 JSON 형식으로 변환하여 클라이언트에게 전송
        objectMapper.writeValue(response.getOutputStream(), BaseResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message(authException.getMessage())
                .build()
        );
    }
}
