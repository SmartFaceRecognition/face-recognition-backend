package com.Han2m.portLogistics.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRequestDto {
    private String accountId;
    private String password;
    private List<String> roles;
}