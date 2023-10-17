package com.Han2m.portLogistics.user.entity;

import com.Han2m.portLogistics.admin.entitiy.Account;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("worker")
public class Worker extends Person{

    //직급
    private String position;

    private String faceUrl;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;

    //담당하고 있는 외부인
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<Guest> guests = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<Control> controlList = new ArrayList<>();

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    @Builder
    public Worker(String nationality, String name, Boolean sex, String birth, String phone,String position, String faceUrl, Account account, List<Guest> guests, List<Control> controlList) {
        super(nationality, name, sex, birth, phone);
        this.position = position;
        this.faceUrl = faceUrl;
        this.account = account;
        this.guests = guests;
        this.controlList = controlList;
    }

    public void updateWorker(ReqWorkerDto reqWorkerDto){
        Optional<ReqWorkerDto> optionalReqWorkerDTO = Optional.ofNullable(reqWorkerDto);

        optionalReqWorkerDTO.map(ReqWorkerDto::getName).ifPresent(this::setName);
        optionalReqWorkerDTO.map(ReqWorkerDto::getPhone).ifPresent(this::setPhone);
        optionalReqWorkerDTO.map(ReqWorkerDto::getPosition).ifPresent(position -> this.position = position);
        optionalReqWorkerDTO.map(ReqWorkerDto::getSex).ifPresent(this::setSex);
        optionalReqWorkerDTO.map(ReqWorkerDto::getNationality).ifPresent(this::setNationality);
        optionalReqWorkerDTO.map(ReqWorkerDto::getBirth).ifPresent(this::setBirth);
    }

    public ResWorkerDto toResWorkerDto(){
        return ResWorkerDto.builder().
                personId(getPersonId()).
                birth(getBirth()).
                faceUrl(getFaceUrl()).
                sex(getSex()).
                name(getName()).
                nationality(getNationality()).
                phone(getPhone()).
                wharfs(getWharfList().stream().map(Wharf::getName).toList()).
                position(getPosition()).build();
    }
}
