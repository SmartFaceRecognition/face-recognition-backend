package com.Han2m.portLogistics.user.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResWorkerDto{

    private String nationality;
    private String name;
    private Boolean sex;
    private String birth;
    private String phone;
    private Long personId;
    private String position;
    private String faceUrl;
    private List<String> wharfs;

}
