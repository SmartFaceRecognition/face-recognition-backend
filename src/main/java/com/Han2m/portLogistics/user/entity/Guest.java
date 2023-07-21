package com.Han2m.portLogistics.user.entity;


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
public class Guest {

    // 07.21. 차별점을 두려면 상시 출입인원을 추가 ?? guest는 견학 or 점검 느낌이고 자주 드나드는 사람은 상시출입증을 발급하도록 (차량번호 등 추가?)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guestID")
    private Long id;
    private String name;
    private String birth;
    private String phone;
    private String ssn;
    private String address;
    private String sex; // boolean


    public Guest(Long id, String name, String birth, String phone, String ssn, String address, String sex) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.ssn = ssn;
        this.address = address;
        this.sex = sex;
    }

    @OneToMany(mappedBy = "guest")
    private List<Status> statusList = new ArrayList<>();

    @OneToMany(mappedBy = "guest")
    private List<Control> controlList = new ArrayList<>();

    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserWharf> userWharfList = new ArrayList<>();
}
