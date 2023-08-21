package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

    //담당하고 있는 외부인
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<Guest> guests = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<Control> controlList = new ArrayList<>();

}
