package com.Han2m.portLogistics.admin.service;

import com.Han2m.portLogistics.admin.dto.LoginRequestDto;
import com.Han2m.portLogistics.admin.dto.LoginResponseDto;
import com.Han2m.portLogistics.admin.dto.UserRequestDto;
import com.Han2m.portLogistics.admin.entitiy.Account;
import com.Han2m.portLogistics.admin.repository.AccountRepository;
import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.entity.Worker;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final WorkerRepository workerRepository;

    // id, pw, role 받아서 관리자가 회원가입을 시키는 것.
    public void addUser(Long workerId, UserRequestDto userRequestDto) {  // workerId 인자 추가
        if (accountRepository.findByAccountId(userRequestDto.getAccountId()).isEmpty()) {
            Account account = Account.builder()
                    .accountId(userRequestDto.getAccountId())
                    .password(passwordEncoder.encode(userRequestDto.getPassword()))
                    .roles(userRequestDto.getRoles())
                    .build();

            Worker worker = workerRepository.findById(workerId).orElseThrow(EntityNotFoundException::new);
            account.setWorker(worker);
            worker.setAccount(account);

            accountRepository.save(account);
        } else {
            throw new RuntimeException("해당 아이디는 이미 존재합니다.");
        }
    }



    // 계정을 바꾸면, 재로그인을 필수적으로 시켜야함 !!
    public LoginResponseDto editAccount(LoginRequestDto loginRequestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentAccountId = auth.getName();

        // 아이디 중복 확인
        accountRepository.findByAccountId(loginRequestDto.getAccountId()).orElseThrow(() -> new RuntimeException("해당 아이디는 이미 존재합니다."));

        // 현재 로그인된 정보 가져오기
        Account account = accountRepository.findByAccountId(currentAccountId).orElseThrow(EntityNotFoundException::new);

        // 현재 로그인된 멤버의 정보 변경
        account.updateInfo(loginRequestDto.getAccountId(), passwordEncoder.encode(loginRequestDto.getPassword()));

        return new LoginResponseDto(account.getAccountId(), account.getPassword());
    }
}