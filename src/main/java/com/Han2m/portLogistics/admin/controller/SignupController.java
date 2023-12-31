package com.Han2m.portLogistics.admin.controller;

import com.Han2m.portLogistics.admin.dto.UserRequestDto;
import com.Han2m.portLogistics.admin.service.AccountService;
import com.Han2m.portLogistics.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.Han2m.portLogistics.exception.ApiResponse.successResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignupController {

    private final AccountService accountService;

    @PostMapping("/worker/{workerId}/account")
    public ApiResponse<Object> addUser(@PathVariable Long workerId,@RequestBody UserRequestDto userRequestDto) {
        accountService.addUser(workerId, userRequestDto);
        return successResponse(userRequestDto);
    }
}