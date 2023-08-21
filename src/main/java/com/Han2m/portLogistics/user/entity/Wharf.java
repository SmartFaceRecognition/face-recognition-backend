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
    private Long wharfId;
    public String place;

    @OneToMany(mappedBy = "wharf", cascade = CascadeType.ALL)
    private List<PersonWharf> PersonWharfList = new ArrayList<>();

    @OneToMany(mappedBy = "wharf", cascade = CascadeType.ALL)
    private List<Status> statusList = new ArrayList<>();

    public Wharf(Long wharfId, String place) {
        this.wharfId = wharfId;
        this.place = place;
    }
}
