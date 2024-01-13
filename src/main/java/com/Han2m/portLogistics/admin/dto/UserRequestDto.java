package com.Han2m.portLogistics.admin.dto;

import lombok.Data;

//0이면 admin 1 이면 worker
@Data
public class UserRequestDto {
    private String accountId;
    private String password;
    private Integer role;
}