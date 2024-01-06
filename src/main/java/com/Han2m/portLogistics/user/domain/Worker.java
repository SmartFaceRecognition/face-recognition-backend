package com.Han2m.portLogistics.user.domain;

import com.Han2m.portLogistics.admin.domain.Account;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("worker")
@SuperBuilder
@NoArgsConstructor
public class Worker extends Person{

    //직급
    private String position;
    private String faceUrl;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;
    //담당하고 있는 외부인
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Guest> guests = new ArrayList<>();
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Control> controlList = new ArrayList<>();

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public void updateWorker(ReqWorkerDto reqWorkerDto){
        this.setName(reqWorkerDto.getName());
        this.setPhone(reqWorkerDto.getPhone());
        this.position = reqWorkerDto.getPosition();
        this.setSex(reqWorkerDto.getSex());
        this.setNationality(reqWorkerDto.getNationality());
        this.setBirth(reqWorkerDto.getBirth());
    }


}
