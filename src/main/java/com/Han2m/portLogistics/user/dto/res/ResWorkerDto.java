package com.Han2m.portLogistics.user.dto.res;

import com.Han2m.portLogistics.user.domain.PersonWharf;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.domain.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResWorkerDto{

    private String nationality;
    private String name;
    private Boolean sex;
    private String birth;
    private String phone;
    private Long workerId;
    private String position;
    private String faceUrl;
    private List<String> wharfs;

    public ResWorkerDto(Worker worker) {
        this.nationality = worker.getNationality();
        this.name = worker.getName();
        this.sex = worker.getSex();
        this.birth = worker.getBirth();
        this.phone = worker.getPhone();
        this.workerId = worker.getPersonId();
        this.position = worker.getPosition();
        this.faceUrl = worker.getFaceUrl();
        this.wharfs = worker.getPersonWharfList().stream().map(PersonWharf::getWharf).map(Wharf::getName).toList();
    }
}
