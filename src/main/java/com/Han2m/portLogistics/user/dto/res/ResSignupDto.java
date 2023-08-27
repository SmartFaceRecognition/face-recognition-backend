package com.Han2m.portLogistics.user.dto.res;

public class ResSignupDto {

    private String memberId;
    private String password;

    public ResSignupDto(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }
}
