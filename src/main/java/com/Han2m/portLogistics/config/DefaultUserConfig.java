package com.Han2m.portLogistics.config;

import com.Han2m.portLogistics.admin.entitiy.Account;
import com.Han2m.portLogistics.admin.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class DefaultUserConfig {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDefaultUser() {
        return args -> {
            if (accountRepository.findById("ADMIN").isEmpty()) {
                Account account = Account.builder()
                        .id("test1")
                        .password(passwordEncoder.encode("1234"))
                        .roles(Collections.singletonList("ADMIN"))
                        .build();
                accountRepository.save(account);
            }
        };
    }
}
