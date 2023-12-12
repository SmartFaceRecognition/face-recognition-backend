package com.Han2m.portLogistics.user.domain;


import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Entity
@Getter
@DiscriminatorValue("guest")
@SuperBuilder
@NoArgsConstructor
public class Guest extends Person{

    //신청 종류 임시출입, 사진촬영, 승선허가서 출입신청, 적재허가서 출입신청
    private String reason;

    //목적
    private String goal;

    // 출입일
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "workerId")
    //담당자
    private Worker worker;

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void updateGuest(ReqGuestDto reqGuestDto){

        this.setName(reqGuestDto.getName());
        this.setPhone(reqGuestDto.getPhone());
        this.setSex(reqGuestDto.getSex());
        this.setNationality(reqGuestDto.getNationality());
        this.setBirth(reqGuestDto.getBirth());
        this.goal = reqGuestDto.getGoal();
        this.reason = reqGuestDto.getReason();
        this.date = reqGuestDto.getDate();

    }
    
    public ResGuestDto toResGuestDto(){
        return ResGuestDto.builder().
                personId(getPersonId()).
                birth(getBirth()).
                sex(getSex()).
                name(getName()).
                nationality(getNationality()).
                phone(getPhone()).
                goal(getGoal()).
                reason(getReason()).
                date(getDate()).
                wharfs(getPersonWharfList().stream().map(PersonWharf::getWharf).map(Wharf::getName).collect(Collectors.toList())).
                build();
    }
    
}
