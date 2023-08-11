package com.Han2m.portLogistics.config;

import com.Han2m.portLogistics.admin.entitiy.Member;
import com.Han2m.portLogistics.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class DefaultUserConfig {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDefaultUser() {
        return args -> {
            if (memberRepository.findByMemberId("ADMIN").isEmpty()) {
                Member member = Member.builder()
                        .memberId("test111")
                        .password(passwordEncoder.encode("1234"))
                        .roles(Collections.singletonList("USER"))
                        .build();
                memberRepository.save(member);
            }
        };
    }
}
