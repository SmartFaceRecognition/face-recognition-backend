package com.Han2m.portLogistics.user.entity;

import com.Han2m.portLogistics.admin.entitiy.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("worker")
public class Worker extends Person{

    //소속된 회사
    private String company;
    //직급
    private String position;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;


    //담당하고 있는 외부인
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<Guest> guests = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<Control> controlList = new ArrayList<>();

}
