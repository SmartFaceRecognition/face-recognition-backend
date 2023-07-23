package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GuestWharf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guestWharfID")
    private Long id;

    public GuestWharf(Guest guest, Wharf wharf) {
        this.guest = guest;
        this.wharf = wharf;
    }

    @ManyToOne
    @JoinColumn(name = "guestID")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "wharfID")
    private Wharf wharf;
}
