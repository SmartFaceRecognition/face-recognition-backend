package com.Han2m.portLogistics.user.dto.res;

import com.Han2m.portLogistics.user.entity.Status;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.entity.Worker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ResStatusDto {

    private Long statusId;
    private Long wharfId;
    private Long workerId;
    private Timestamp enterTime;
    private Timestamp outTime;

    public ResStatusDto(Status status) {
        statusId = status.getStatusId();
        wharfId = status.getWharf().getWharfId();
        workerId = status.getWorker().getPersonId();
        enterTime = status.getEnterTime();
        outTime = status.getOutTime();
    }
}
