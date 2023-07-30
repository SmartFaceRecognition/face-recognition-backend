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
public class Person {

    // 국적, 성별, 생년월일 등은 @Column(updatable = false)이 필요할 수도 있겠다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personID")
    private Long id;
    private String nationality;
    private String name;
    private Boolean isWorker;
    private Boolean sex;
    private String birth;
    private String phone;

    public Person(Long id, String nationality, String name, Boolean isWorker, Boolean sex, String birth, String phone) {
        this.id = id;
        this.nationality = nationality;
        this.name = name;
        this.isWorker = isWorker;
        this.sex = sex;
        this.birth = birth;
        this.phone = phone;
    }


    //@OneToOne(cascade = CascadeType.ALL) <<-- 안되면 참고 !!
    @OneToOne(mappedBy = "person")
    private Guest guest;

    @OneToOne(mappedBy = "person")
    private Worker worker;

    @OneToMany(mappedBy = "person")
    private List<Status> statusList = new ArrayList<>();

    @OneToMany(mappedBy = "person")
    private List<Control> controlList = new ArrayList<>();

    // 여기 상속관계를 추가해야될 수도 있음
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PersonWharf> personWharfList = new ArrayList<>();
}
