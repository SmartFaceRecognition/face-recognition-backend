package com.Han2m.portLogistics.user.dto.req;

import com.Han2m.portLogistics.user.dto.PersonDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReqWorkerDto extends PersonDto {
    private String position;

    private List<String> wharfs;
}
