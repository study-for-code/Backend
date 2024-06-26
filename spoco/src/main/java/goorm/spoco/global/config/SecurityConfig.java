package goorm.spoco.global.config;

import goorm.spoco.global.common.auth.JwtAuthFilter;
import goorm.spoco.global.common.auth.SpocoUserDetailsService;
import goorm.spoco.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final SpocoUserDetailsService spocoUserDetailsService;
    private final JwtUtil jwtUtil;
    private final SpocoAccessDeniedHandler spocoAccessDeniedHandler;
    private final SpocoAuthenticationEntryPoint spocoAuthenticationEntryPoint;

    // 인증 없이 접근할 수 있는 경로를 정의하는 화이트리스트
    private static final String[] AUTH_WHITELIST = {
            "/login/**", "/auth/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 보호 기능을 비활성화하고 CORS 설정을 기본값으로 설정한다.
        http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults());

        // 세션 관리 정책을 STATELESS로 설정하여 세션을 사용하지 않도록 한다.
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 폼 로그인과 HTTP Basic 인증을 비활성화
        http.formLogin(form -> form.disable())
                .httpBasic(AbstractHttpConfigurer::disable);

        // 커스텀 JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
        http.addFilterBefore(new JwtAuthFilter(spocoUserDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // 예외 처리 핸들러를 설정
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        .authenticationEntryPoint(spocoAuthenticationEntryPoint)
                        .accessDeniedHandler(spocoAccessDeniedHandler));

        // 인증 및 권한 설정을 구성
        http.authorizeHttpRequests(authorize ->
                authorize
                        // 화이트리스트에 포함된 경로는 인증 없이 접근을 허용
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        // 그 외의 요청은 모두 인증된 사용자에게만 허용합니다.
                        .anyRequest().permitAll());

        // SecurityFilterChain 객체를 빌드하여 반환
        return http.build();
    }
}
