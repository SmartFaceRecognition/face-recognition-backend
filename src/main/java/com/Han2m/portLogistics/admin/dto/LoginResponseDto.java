package com.Han2m.portLogistics.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String memberId;
    private String password;
}
