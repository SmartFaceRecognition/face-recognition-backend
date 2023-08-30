package com.Han2m.portLogistics.admin.service;

import com.Han2m.portLogistics.admin.dto.LoginRequestDto;
import com.Han2m.portLogistics.admin.dto.UserRequestDto;
import com.Han2m.portLogistics.admin.entitiy.Member;
import com.Han2m.portLogistics.admin.repository.MemberRepository;
import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.entity.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // id, pw, role 받아서 관리자가 회원가입을 시키는 것.
    public void addUser(UserRequestDto userRequestDto) {
        if (memberRepository.findByMemberId(userRequestDto.getMemberId()).isEmpty()) {
            Member member = Member.builder()
                    .memberId(userRequestDto.getMemberId())
                    .password(passwordEncoder.encode(userRequestDto.getPassword()))
                    .roles(userRequestDto.getRoles())
                    .build();
            memberRepository.save(member);
        } else {
            throw new RuntimeException("ID 중복입니다.");
        }
    }


    // 계정을 바꾸면, 재로그인을 필수적으로 시켜야함 !!
    public LoginRequestDto editAccount(LoginRequestDto loginRequestDto) {
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

        // Before 확인용
        log.info("Before Change - MemberId: {}, Password: {}", currentMember.getMemberId(), currentMember.getPassword());

        // 현재 로그인된 멤버의 정보 변경
        currentMember.updateInfo(loginRequestDto.getMemberId(), passwordEncoder.encode(loginRequestDto.getPassword()));

        // Authentication 정보 업데이트
        Authentication newAuth = new UsernamePasswordAuthenticationToken(currentMember.getMemberId(), currentMember.getPassword(), auth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        // After 확인용
        log.info("After Change - MemberId: {}, Password: {}", currentMember.getMemberId(), currentMember.getPassword());

        return new LoginRequestDto(currentMember.getMemberId(), currentMember.getPassword());
    }
}