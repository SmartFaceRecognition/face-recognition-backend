package com.Han2m.portLogistics.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonDto {

    // 국적, 성명, 생년월일, 연락처, 직책, 사진, 부두
    private Long id; // 인원의 고유 아이디
    private String nationality;
    private String name;
    private String birth;
    private String phone;
    private String position;
    private String faceUrl;
    private List<String> wharfs; // 직원 1명이 2개 이상의 부두에 허가받은 경우를 생각

}
