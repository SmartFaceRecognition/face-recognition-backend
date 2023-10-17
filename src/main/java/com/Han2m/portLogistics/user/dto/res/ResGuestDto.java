package com.Han2m.portLogistics.user.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResGuestDto{

    private String nationality;
    private String name;
    private Boolean sex;
    private String birth;
    private String phone;
    private Long personId;
    private LocalDate date;
    private String reason;
    private String goal;
    private Long workerId;
    private List<String> wharfs;



}