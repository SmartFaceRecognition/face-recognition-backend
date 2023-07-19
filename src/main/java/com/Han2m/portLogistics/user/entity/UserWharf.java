package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserWharf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userWharfID")
    private Long id;

    public UserWharf(Person person, Wharf wharf) {
        this.person = person;
        this.wharf = wharf;
    }

    @ManyToOne
    @JoinColumn(name = "personID")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "wharfID")
    private Wharf wharf;
}
