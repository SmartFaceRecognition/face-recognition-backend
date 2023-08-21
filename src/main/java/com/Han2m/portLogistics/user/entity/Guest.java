package com.Han2m.portLogistics.user.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
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


}
