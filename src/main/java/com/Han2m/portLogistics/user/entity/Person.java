package com.Han2m.portLogistics.user.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private String faceUrl;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PersonWharf> PersonWharfList = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Status> statusList = new ArrayList<>();


    public List<String> getWharfPlaces() {
        return PersonWharfList.stream()
                .map(PersonWharf::getWharf)
                .map(Wharf::getPlace)
                .collect(Collectors.toList());
    }

}
