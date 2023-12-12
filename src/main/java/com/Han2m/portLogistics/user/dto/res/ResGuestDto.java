package com.Han2m.portLogistics.user.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResGuestDto{

    private String nationality;
    private String name;
    private Boolean sex;
    private String birth;
    private String phone;
    private Long personId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String reason;
    private String goal;
    private Long workerId;
    private List<String> wharfs;



}