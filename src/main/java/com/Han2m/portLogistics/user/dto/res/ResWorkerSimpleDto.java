package com.Han2m.portLogistics.user.dto.res;

import com.Han2m.portLogistics.user.domain.Permission;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.domain.Worker;
import lombok.Data;

import java.util.List;

@Data
public class ResWorkerSimpleDto {

    private Long workerId;
    private String nationality;
    private String name;
    private Boolean sex;
    private String position;
    private List<String> wharfs;

    public ResWorkerSimpleDto(Worker worker) {
        this.workerId = worker.getPersonId();
        this.nationality = worker.getNationality();
        this.name = worker.getName();
        this.sex = worker.getSex();
        this.position = worker.getPosition();
        this.wharfs = worker.getPermissionList().stream().map(Permission::getWharf).map(Wharf::getName).toList();
    }
}
