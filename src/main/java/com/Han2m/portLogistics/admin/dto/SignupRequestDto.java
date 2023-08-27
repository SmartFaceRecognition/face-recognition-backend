package com.Han2m.portLogistics.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SignupRequestDto {

    @NotBlank(message = "필수값 입니다")
    private String memberId;
    @NotBlank(message = "필수값 입니다")
    private String password;
    @NotBlank(message = "필수값 입니다")
    private String nationality;
    @NotBlank(message = "필수값 입니다")
    private String name;
    @NotNull(message = "필수값 입니다")
    private Boolean sex;
    @NotBlank(message = "필수값 입니다")
    private String birth;
    @NotBlank(message = "필수값 입니다")
    private String phone;
    @NotBlank(message = "필수값 입니다")
    private String company;
    @NotBlank(message = "필수값 입니다")
    private String position;
    @NotBlank(message = "필수값 입니다")
    private List<String> wharf;
    @NotBlank(message = "필수값 입니다")
    private String faceUrl;


}