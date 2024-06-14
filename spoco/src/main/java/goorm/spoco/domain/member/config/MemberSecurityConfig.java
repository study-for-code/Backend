//package goorm.spoco.domain.member.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//
//@Configuration
//@EnableWebSecurity
//public class MemberSecurityConfig {
//
//    //== H2 콘솔에 Security 등록 ==//
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
//                .csrf((csrf) -> csrf
//                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
//                .headers((headers) -> headers
//                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
//                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
//                .formLogin((formLogin) -> formLogin
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/"))
//                .logout((logout) -> logout
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true));
//
//        return http.build();
//    }
//
//    //== password : password 를 빈등록 하여 객체 주입 ==//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    //== 사용자 인증 : AuthenticationManager @Bean 등록 ==//
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//}
