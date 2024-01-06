package com.Han2m.portLogistics.admin.controller;

import com.Han2m.portLogistics.admin.dto.LoginRequestDto;
import com.Han2m.portLogistics.admin.dto.TokenDto;
import com.Han2m.portLogistics.admin.dto.UserEditPasswordDto;
import com.Han2m.portLogistics.admin.dto.UserRequestDto;
import com.Han2m.portLogistics.admin.service.AccountService;
import com.Han2m.portLogistics.admin.service.LoginService;
import com.Han2m.portLogistics.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.Han2m.portLogistics.exception.ApiResponse.successResponse;
import static com.Han2m.portLogistics.exception.ApiResponse.successResponseNoContent;

@Tag(name = "관리자 등록/로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminController {
    private final LoginService loginService;
    private final AccountService accountService;

    @Operation(summary = "직원 계정 생성")
    @PostMapping("/worker/{workerId}/account")
    public ApiResponse<?> addUser(@PathVariable Long workerId, @RequestBody UserRequestDto userRequestDto) {
        accountService.addUser(workerId, userRequestDto);
        return successResponseNoContent();
    }

    @Operation(summary = "계정 로그인")
    @PostMapping("/login")
    public ApiResponse<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        TokenDto token = loginService.login(loginRequestDto);
        return successResponse(token);
    }

    @Operation(summary = "계정 비밀번호 변경")
    @PutMapping("/worker/password")
    public ApiResponse<?> editPassword(@RequestBody UserEditPasswordDto userEditPasswordDto) {
        accountService.editAccount(userEditPasswordDto);
        return successResponseNoContent();
    }
}
