package com.Han2m.portLogistics.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuestDto {

    private Long id;
    private String name;
    private String birth;
    private String phone;
    private String ssn;
    private String address;
    private String sex;
    private List<String> wharfs;

}
