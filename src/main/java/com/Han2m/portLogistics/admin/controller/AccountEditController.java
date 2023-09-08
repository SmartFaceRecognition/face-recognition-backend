package com.Han2m.portLogistics.admin.controller;

import com.Han2m.portLogistics.admin.dto.LoginRequestDto;
import com.Han2m.portLogistics.admin.dto.LoginResponseDto;
import com.Han2m.portLogistics.admin.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.Han2m.portLogistics.response.ResBody.successResponse;

@RestController
@RequiredArgsConstructor
public class AccountEditController {

    private final AccountService accountService;

    // 계정 변경시 로그인 redirect 필수.
    @PutMapping("/account")
    public ResponseEntity<Object> editAccount(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = accountService.editAccount(loginRequestDto);
        return successResponse(loginResponseDto);
    }
}
