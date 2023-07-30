package com.Han2m.portLogistics.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonDto {

    private Long id; // 인원의 고유 아이디
    private String nationality;
    private String name;
    private boolean isWorker;
    private boolean sex;
    private String birth;
    private String phone;
    private List<String> wharfs;

}
