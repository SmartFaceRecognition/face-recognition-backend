package com.Han2m.portLogistics.admin.controller;

import com.Han2m.portLogistics.admin.dto.LoginRequestDto;
import com.Han2m.portLogistics.admin.dto.TokenDto;
import com.Han2m.portLogistics.admin.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginRequestDto loginRequestDto) {
        String accountId = loginRequestDto.getAccountId();
        String password = loginRequestDto.getPassword();
        TokenDto tokenDto = loginService.login(accountId, password);
        return tokenDto;
    }
}
