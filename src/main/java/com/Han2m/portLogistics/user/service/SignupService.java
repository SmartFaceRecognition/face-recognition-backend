package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.admin.entitiy.Member;
import com.Han2m.portLogistics.admin.repository.MemberRepository;
import com.Han2m.portLogistics.user.dto.req.ReqSignupDto;
import com.Han2m.portLogistics.user.dto.res.ResSignupDto;
import com.Han2m.portLogistics.user.entity.Signup;
import com.Han2m.portLogistics.user.entity.Worker;
import com.Han2m.portLogistics.user.repository.PersonRepository;
import com.Han2m.portLogistics.user.repository.PersonWharfRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class SignupService {

    private final WorkerRepository workerRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public ResSignupDto applyDefaultAccountToMyAccount(ReqSignupDto reqSignupDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentMemberId = auth.getName();

        Optional<Member> memberOptional = memberRepository.findByMemberId(currentMemberId);
        if(!memberOptional.isPresent()) {
            throw new RuntimeException("해당 멤버가 없습니다.");
        }

        // 아이디 중복 확인
        Optional<Worker> existingWorkerOptional = workerRepository.findBySignupMemberId(reqSignupDto.getMemberId());
        if(existingWorkerOptional.isPresent()) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        // 현재 로그인된 정보 가져오기
        Member currentMember = memberOptional.get();

        log.info("Before Change - MemberId: {}, Password: {}", currentMember.getMemberId(), currentMember.getPassword());

        Signup newSignup = new Signup();
        newSignup.updateInfo(reqSignupDto.getMemberId(), passwordEncoder.encode(reqSignupDto.getPassword()));

        Worker newWorker = new Worker();
        newWorker.setSignup(newSignup);
        newSignup.setWorker(newWorker);

        workerRepository.save(newWorker);

        // 기존 계정 삭제
        memberRepository.delete(currentMember);

        log.info("After Change - MemberId: {}, Password: {}", newSignup.getMemberId(), newSignup.getPassword());

        // 08.27.) db에 저장 자체는 암호화 해서 되고, postman에서 결과값 반환은 비밀번호가 그대로 노출 됨.
        return new ResSignupDto(newSignup.getMemberId(), newSignup.getPassword());
    }
}
