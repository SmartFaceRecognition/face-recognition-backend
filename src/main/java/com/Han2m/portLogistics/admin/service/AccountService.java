package com.Han2m.portLogistics.admin.service;

import com.Han2m.portLogistics.admin.domain.Account;
import com.Han2m.portLogistics.admin.domain.Role;
import com.Han2m.portLogistics.admin.dto.UserEditPasswordDto;
import com.Han2m.portLogistics.admin.dto.UserRequestDto;
import com.Han2m.portLogistics.admin.repository.AccountRepository;
import com.Han2m.portLogistics.exception.CustomException;
import com.Han2m.portLogistics.exception.CustomException.EntityNotFoundException;
import com.Han2m.portLogistics.user.domain.Worker;
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

    @Transactional(readOnly = true)
    public Boolean isAccountPresent(String accountId) {
        return accountRepository.existsAccountByAccountId(accountId);
    }

    // id, pw, role 받아서 관리자가 회원가입을 시키는 것.
    public void addUser(Long workerId, UserRequestDto userRequestDto) {

        Worker worker = workerRepository.findById(workerId).orElseThrow(()-> new EntityNotFoundException("해당 Worker는 존재하지 않습니다."));

        if(!isAccountPresent(userRequestDto.getAccountId())){

            Role role = (userRequestDto.getRole() == 0) ? Role.ROLE_ADMIN : Role.ROLE_WORKER;

            Account account = Account.builder()
                    .accountId(userRequestDto.getAccountId())
                    .password(passwordEncoder.encode(userRequestDto.getPassword()))
                    .role(role)
                    .build();

            account.setWorker(worker);

            Account savedAccount = accountRepository.save(account);
            worker.setAccount(savedAccount);
        }
        else {
            throw new CustomException.DuplicateIdException();
        }
    }


    // 계정을 바꾸면, 재로그인을 필수적으로 시켜야함 !!
    public void editAccount(UserEditPasswordDto userEditPasswordDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentAccountId = auth.getName();

        // 현재 로그인된 정보 가져오기
        Account account = accountRepository.findByAccountId(currentAccountId).orElseThrow(()-> new EntityNotFoundException("해당 ID의 계정은 존재하지 않습니다."));

        // 현재 로그인된 멤버의 정보 변경
        account.editPassword(passwordEncoder.encode(userEditPasswordDto.getPassword()));
    }
}