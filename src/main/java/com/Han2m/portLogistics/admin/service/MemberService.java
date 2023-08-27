package com.Han2m.portLogistics.admin.service;

import com.Han2m.portLogistics.admin.dto.SignupRequestDto;
import com.Han2m.portLogistics.admin.entitiy.Member;
import com.Han2m.portLogistics.admin.repository.MemberRepository;
import com.Han2m.portLogistics.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member signup(SignupRequestDto signupRequestDto) {
        if (memberRepository.findByMemberId(signupRequestDto.getMemberId()).isPresent()){
            throw new RuntimeException("이미 사용중인 ID입니다.");
        }

        Member member = Member.builder()
                .memberId(signupRequestDto.getMemberId())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .nationality(signupRequestDto.getNationality())
                .name(signupRequestDto.getName())
                .sex(signupRequestDto.getSex())
                .birth(signupRequestDto.getBirth())
                .phone(signupRequestDto.getPhone())
                .faceUrl(signupRequestDto.getFaceUrl())
                .company(signupRequestDto.getCompany())
                .position(signupRequestDto.getPosition())
                .roles(Collections.singletonList("DEFAULT_ROLE")) // default role (이 부분은 원하는 대로 설정하실 수 있습니다)
                .build();

        return memberRepository.save(member);
    }
}