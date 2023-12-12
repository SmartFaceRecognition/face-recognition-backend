package com.Han2m.portLogistics.user.domain;

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

    public String name;

    @OneToMany(mappedBy = "wharf", cascade = CascadeType.ALL)
    private List<PersonWharf> personWharfList = new ArrayList<>();

    @OneToMany(mappedBy = "wharf", cascade = CascadeType.ALL)
    private List<Status> statusList = new ArrayList<>();

    public Wharf(Long wharfId, String name) {
        this.wharfId = wharfId;
        this.name = name;
    }

}
