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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personID")
    private Long id;
    private String nationality;
    private String name;
    private Boolean isWorker; // 직원 True, 손님 False
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

    @OneToMany(mappedBy = "person")
    private List<Status> statusList = new ArrayList<>();

    @OneToMany(mappedBy = "person")
    private List<Control> controlList = new ArrayList<>();

    // 여기 상속관계를 추가해야될 수도 있음
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PersonWharf> personWharfList = new ArrayList<>();
}
