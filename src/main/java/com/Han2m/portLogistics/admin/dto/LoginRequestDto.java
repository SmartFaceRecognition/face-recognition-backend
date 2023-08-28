package com.Han2m.portLogistics.admin.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String memberId;
    private String password;

    public LoginRequestDto(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }
}
