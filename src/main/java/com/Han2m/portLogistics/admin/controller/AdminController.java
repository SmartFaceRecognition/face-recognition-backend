package com.Han2m.portLogistics.admin.controller;

import com.Han2m.portLogistics.admin.dto.UserRequestDto;
import com.Han2m.portLogistics.admin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.Han2m.portLogistics.response.ResBody.successResponse;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @PostMapping("/admin/addUser")
    public ResponseEntity<Object> addUser(@RequestBody UserRequestDto userRequestDto) {
        memberService.addUser(userRequestDto);
        return successResponse(userRequestDto);
    }
}