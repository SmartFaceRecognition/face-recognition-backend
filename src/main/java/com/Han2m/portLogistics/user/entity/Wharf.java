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
public class Wharf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wharfID")
    private Long id;
    public String place;

    public Wharf(Long id, String place) {
        this.id = id;
        this.place = place;
    }

//    @OneToMany(mappedBy = "wharf")
//    private List<Control> controlList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "personWharfID")
    private List<PersonWharf> personWharfList = new ArrayList<>();

    // guestWharfList 삭제
}
