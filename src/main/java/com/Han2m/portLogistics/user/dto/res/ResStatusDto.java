package com.Han2m.portLogistics.user.dto.res;

import com.Han2m.portLogistics.user.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ResStatusDto {

    private Long statusId;
    private Long wharfId;
    private Long personId;
    private Timestamp enterTime;
    private Timestamp outTime;

    public ResStatusDto(Status status) {
        statusId = status.getStatusId();
        wharfId = status.getWharf().getWharfId();
        personId = status.getPerson().getPersonId();
        enterTime = status.getEnterTime();
        outTime = status.getOutTime();
    }
}
