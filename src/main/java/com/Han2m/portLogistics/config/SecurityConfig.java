package com.Han2m.portLogistics.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtTokenProvider jwtTokenProvider;


    // security 6.1 최신버전으로 문법을 조금 다르게 사용해야함.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // 서버에 저장하지 않아서 csrf 공격에 대한 옵션은 꺼둔다.
                //폼 로그인 안함
                .formLogin(AbstractHttpConfigurer::disable)
                //세션 안씀
                .sessionManagement((httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .authorizeHttpRequests((authorizeRequests) -> {

                    /**
                     * 1) ROLE_ 은 붙이면 안 된다. hasAnyRole()을 사용할 때 자동으로 ROLE_ 이 붙기 때문
                     * 2) ADMIN과 WORKER 로 나누어서, 등록은 ADMIN만 되고 WORKER는 조회만 되게끔 설정하기
                    **/

                  //  authorizeRequests.requestMatchers("/worker/**").hasRole("ADMIN");
//                    authorizeRequests.requestMatchers("/guest/**").hasRole("ADMIN");
//                    authorizeRequests.requestMatchers("/worker/{id}").hasRole("WORKER");
//                    authorizeRequests.requestMatchers("/signup").hasRole("ADMIN");
                    authorizeRequests.anyRequest().permitAll(); // 그 외의 요청은 다 허용
                })

                // JwtAuthenticationFilter를 먼저 적용
                //  -->> jwt token을 사용하기 위함, 이후 postman으로 할 땐 token을 넣어서 실행
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}