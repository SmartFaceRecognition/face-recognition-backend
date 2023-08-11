package com.Han2m.portLogistics.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig{


    // security 6.1 최신버전으로 문법을 조금 다르게 사용해야함.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // 서버에 저장하지 않아서 csrf 공격에 대한 옵션은 꺼둔다.
                .authorizeHttpRequests((authorizeRequests) -> {
                    // ROLE_은 붙이면 안 된다. hasAnyRole()을 사용할 때 자동으로 ROLE_이 붙기 때문
//                    authorizeRequests.requestMatchers("/login").permitAll();
                    authorizeRequests.anyRequest().permitAll(); // 그 외의 요청은 다 허용
                    //authorizeRequests.requestMatchers("/**").hasAnyRole("ADMIN", "USER");
                })
                .formLogin((formLogin) -> {
                    formLogin.loginPage("/login"); // 권한이 필요한 요청은 로그인 화면으로 리다이렉트
                })
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}