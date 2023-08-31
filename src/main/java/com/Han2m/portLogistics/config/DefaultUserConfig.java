//package com.Han2m.portLogistics.config;
//
//import com.Han2m.portLogistics.admin.entitiy.Member;
//import com.Han2m.portLogistics.admin.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Collections;
//
//@Configuration
//@RequiredArgsConstructor
//public class DefaultUserConfig {
//
//    // 기능 테스트 편의를 위해 남겨둠. 실제 서비스 때는 파일 자체를 삭제해도 상관 없는 부분.
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Bean
//    CommandLineRunner initDefaultUsers() {
//        return args -> {
//            createDefaultUser("ADMIN1", "admin1", "ADMIN");
//            createDefaultUser("ADMIN2", "admin2", "ADMIN");
//            createDefaultUser("WORKER1", "worker1", "WORKER");
//            createDefaultUser("WORKER2", "worker2", "WORKER");
//        };
//    }
//
//    private void createDefaultUser(String memberId, String password, String role) {
//        if (memberRepository.findByMemberId(memberId).isEmpty()) {
//            Member member = Member.builder()
//                    .memberId(memberId)
//                    .password(passwordEncoder.encode(password))
//                    .roles(Collections.singletonList(role))
//                    .build();
//            memberRepository.save(member);
//        }
//    }
//}