package com.Han2m.portLogistics.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDto {

    // 국적, 성명, 생년월일, 연락처, 직책, 사진
    private Long id; // 인원의 고유 아이디
    private String nationality;
    private String name;
    private String birth;
    private String phone;
    private String position;
    private String faceUrl;

}
