package com.Han2m.portLogistics.user.dto.res;

import com.Han2m.portLogistics.user.entity.Worker;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

    public ResWorkerDto(Worker worker) {
        nationality = worker.getNationality();
        name = worker.getName();
        sex = worker.getSex();
        birth = worker.getBirth();
        phone = worker.getPhone();
        personId = worker.getPersonId();
        position = worker.getPosition();
        faceUrl = worker.getFaceUrl();
        wharfs = worker.getWharfPlaces();
    }
}
