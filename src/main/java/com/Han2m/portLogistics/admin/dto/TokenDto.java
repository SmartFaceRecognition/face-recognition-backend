package com.Han2m.portLogistics.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenDto {

    private String grantType;
    private String accessToken;
}
