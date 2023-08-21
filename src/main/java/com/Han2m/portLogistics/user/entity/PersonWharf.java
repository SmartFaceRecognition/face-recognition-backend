package com.Han2m.portLogistics.user.entity;

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

    public PersonWharf(Person person, Wharf wharf) {
        this.person = person;
        this.wharf = wharf;
    }

    @ManyToOne
    @JoinColumn(name = "personId")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "wharfId")
    private Wharf wharf;
}
