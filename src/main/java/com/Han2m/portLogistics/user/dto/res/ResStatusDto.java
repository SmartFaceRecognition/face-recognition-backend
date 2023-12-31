package com.Han2m.portLogistics.user.dto.res;

import com.Han2m.portLogistics.user.domain.Status;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResStatusDto {

    private Long statusId;
    private Long wharfId;
    private Long personId;
    private String name;
    private boolean isWorker;
    private Timestamp enterTime;
    private Timestamp outTime;

    public ResStatusDto(Status status) {
        statusId = status.getStatusId();
        wharfId = status.getWharf().getWharfId();
        personId = status.getPerson().getPersonId();
        name = status.getPerson().getName();
        isWorker = status.getPerson().isWorker();
        enterTime = status.getEnterTime();
        outTime = status.getOutTime();
    }
}
