package com.Han2m.portLogistics.user.dto.res;

import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.entity.Worker;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResWorkerDto extends PersonDto {

    private Long personId;
    private String position;
    private String faceUrl;
    private String fingerprint;
    private List<String> wharfs;

    public ResWorkerDto(Worker worker) {
        super.setName(worker.getName());
        super.setSex(worker.getSex());
        super.setBirth(worker.getBirth());
        super.setPhone(worker.getPhone());
        super.setNationality(worker.getNationality());
        personId = worker.getPersonId();
        position = worker.getPosition();
        faceUrl = worker.getFaceUrl();
        fingerprint = worker.getFingerprint();
        wharfs = worker.getWharfPlaces();
    }
}
