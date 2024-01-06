package com.Han2m.portLogistics.admin.service;

import com.Han2m.portLogistics.admin.domain.Account;
import com.Han2m.portLogistics.admin.repository.AccountRepository;
import com.Han2m.portLogistics.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {



    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String accountId){
        return accountRepository.findByAccountId(accountId)
                .map(this::createUserDetails)
                .orElseThrow(CustomException.DuplicateIdException::new);
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Account account) {
        return User.builder()
                .username(account.getAccountId())
                .password(account.getPassword())
                .roles(account.getRoles().get(0))
                .build();
    }

}
