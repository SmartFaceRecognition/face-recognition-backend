package com.Han2m.portLogistics.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PersonWharf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PersonWharfId;

    @ManyToOne
    @JoinColumn(name = "personId")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "wharfId")
    private Wharf wharf;

    public PersonWharf(Person person, Wharf wharf) {
        this.person = person;
        this.wharf = wharf;
    }


}