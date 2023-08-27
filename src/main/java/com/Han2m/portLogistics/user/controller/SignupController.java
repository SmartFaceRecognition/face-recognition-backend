package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.req.ReqSignupDto;
import com.Han2m.portLogistics.user.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.Han2m.portLogistics.response.ResBody.successResponse;

@RestController
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @PutMapping("/applyDefaultAccount")
    public ResponseEntity<Object> applyDefaultAccountToMyAccount(@RequestBody ReqSignupDto reqSignupDto) {
        signupService.applyDefaultAccountToMyAccount(reqSignupDto);
        return successResponse(reqSignupDto);
    }
}
