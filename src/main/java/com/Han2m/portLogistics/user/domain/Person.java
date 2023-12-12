package com.Han2m.portLogistics.user.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@SuperBuilder
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;
    private String nationality;
    private String name;
    private Boolean sex;
    private String birth;
    private String phone;

    @Builder.Default
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PersonWharf> personWharfList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Status> statusList = new ArrayList<>();


}
