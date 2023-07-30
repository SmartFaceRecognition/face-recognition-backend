package com.Han2m.portLogistics.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerDto extends PersonDto {
    private String position;
    private String faceUrl;
    private String fingerPrint;
}
