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
public class User_Wharf_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userWharfID")
    private Long id;

    public User_Wharf_Entity(PersonEntity personEntity, WharfEntity wharfEntity) {
        this.personEntity = personEntity;
        this.wharfEntity = wharfEntity;
    }

    @ManyToOne
    @JoinColumn(name = "userID")
    private PersonEntity personEntity;

    @ManyToOne
    @JoinColumn(name = "wharfID")
    private WharfEntity wharfEntity;


}
