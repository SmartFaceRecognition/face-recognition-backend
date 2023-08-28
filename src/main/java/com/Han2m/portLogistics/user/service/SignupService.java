package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.admin.dto.LoginRequestDto;
import com.Han2m.portLogistics.admin.entitiy.Member;
import com.Han2m.portLogistics.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignupService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginRequestDto applyDefaultAccountToMyAccount(LoginRequestDto loginRequestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentMemberId = auth.getName();

        Optional<Member> memberOptional = memberRepository.findByMemberId(currentMemberId);
        if(!memberOptional.isPresent()) {
            throw new RuntimeException("해당 멤버가 없습니다.");
        }

        // 아이디 중복 확인
        Optional<Member> existingMemberOptional = memberRepository.findByMemberId(loginRequestDto.getMemberId());
        if(existingMemberOptional.isPresent()) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        // 현재 로그인된 정보 가져오기
        Member currentMember = memberOptional.get();

        // Before
        log.info("Before Change - MemberId: {}, Password: {}", currentMember.getMemberId(), currentMember.getPassword());

        // 현재 로그인된 멤버의 정보 변경
        currentMember.updateInfo(loginRequestDto.getMemberId(), passwordEncoder.encode(loginRequestDto.getPassword()));

        // Authentication 정보 업데이트
        Authentication newAuth = new UsernamePasswordAuthenticationToken(currentMember.getMemberId(), currentMember.getPassword(), auth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        // After
        log.info("After Change - MemberId: {}, Password: {}", currentMember.getMemberId(), currentMember.getPassword());

        return new LoginRequestDto(currentMember.getMemberId(), currentMember.getPassword());
    }
}