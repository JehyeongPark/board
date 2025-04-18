package com.kr.board.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 인가(접근권한) 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/boardLogin", "/board/css/**", "/board/js/**").permitAll()  // 경로에 대해서 모든 사용자에게 접근을 허용한다는 의미
                        .requestMatchers("/boardLogin").permitAll()
                        .anyRequest().authenticated()
                )

                // 로그인 설정
                .formLogin(form -> form
                        .loginPage("/boardLogin")                                       // 로그인 페이지 경로 설정 (해당 경로를 통해 로그인 진행)
                        .loginProcessingUrl("/login")                                   // 중요: 로그인 요청 처리 URL 명시
                        .defaultSuccessUrl("/")                                         // 로그인 성공 시 이동 경로
                        .failureUrl("/boardLogin?error=true")        // 로그인 실패 시 이동 경로
                        .usernameParameter("uid")                                       // 로그인 폼에서 사용자 아이디를 입력받는 Input 필드 이름 지정 (name="uid")
                        .passwordParameter("pass")                                      // 로그인 폼에서 사용자 비밀번호를 입력받는 Input 필드 이름 지정 (name="pass")
                        .permitAll()
                )

                // 로그아웃 설정
                .logout(logout -> logout                                     // 로그아웃 시 세션 무효화
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))        // 로그아웃 요청 URL (POST 요청 URL)
                        .logoutSuccessUrl("/user/login?success=200")                                   // 로그아웃 성공 시 이동 경로
                        .invalidateHttpSession(true)
                )

                // CSRF 설정 (필요 시 비활성화)
                //.csrf(csrf -> csrf.disable())

                // 세션 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }

    // 비밀번호 암호화 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
