package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Control {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "controlID")
    private Long id;
    private String openTime;
    private String closeTime;

    public Control(Long id, String openTime, String closeTime) {
        this.id = id;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    @ManyToOne
    @JoinColumn(name = "personID")
    private Person person;


    @ManyToOne
    @JoinColumn(name = "guestID")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "wharfID")
    private Wharf wharf;
}
