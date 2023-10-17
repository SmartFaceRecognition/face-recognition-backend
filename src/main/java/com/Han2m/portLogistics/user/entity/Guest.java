package com.Han2m.portLogistics.user.entity;


import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("guest")
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

    @Builder
    public Guest(String nationality, String name, Boolean sex, String birth, String phone, List<Wharf> wharfList, String reason, String goal, LocalDate date, Worker worker) {
        super(nationality, name, sex, birth, phone);
        this.reason = reason;
        this.goal = goal;
        this.date = date;
        this.worker = worker;
    }

    public void updateGuest(ReqGuestDto reqGuestDto){
        Optional<ReqGuestDto> optionalReqGuestDto = Optional.ofNullable(reqGuestDto);

        optionalReqGuestDto.map(ReqGuestDto::getName).ifPresent(this::setName);
        optionalReqGuestDto.map(ReqGuestDto::getPhone).ifPresent(this::setPhone);
        optionalReqGuestDto.map(ReqGuestDto::getSex).ifPresent(this::setSex);
        optionalReqGuestDto.map(ReqGuestDto::getNationality).ifPresent(this::setNationality);
        optionalReqGuestDto.map(ReqGuestDto::getBirth).ifPresent(this::setBirth);
        optionalReqGuestDto.map(ReqGuestDto::getGoal).ifPresent((goal -> this.goal = goal));
        optionalReqGuestDto.map(ReqGuestDto::getReason).ifPresent((reason -> this.reason = reason));
        optionalReqGuestDto.map(ReqGuestDto::getDate).ifPresent(date -> this.date = date);
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
                wharfs(getWharfList().stream().map(Wharf::getName).toList()).build();
    }
    
}
