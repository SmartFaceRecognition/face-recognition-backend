package com.Han2m.portLogistics.user.entity;


import jakarta.persistence.*;
import lombok.Builder;
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
    private Long personId;
    private String nationality;
    private String name;
    private Boolean sex;
    private String birth;
    private String phone;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Wharf> wharfList = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Status> statusList = new ArrayList<>();


    @Builder
    public Person(String nationality, String name, Boolean sex, String birth, String phone) {
        this.nationality = nationality;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.phone = phone;
    }
}
