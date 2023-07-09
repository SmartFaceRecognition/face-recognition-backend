package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class WharfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wharfID")
    private Long id;
    public String place;

    public WharfEntity(Long id, String place) {
        this.id = id;
        this.place = place;
    }

    @ManyToOne
    @JoinColumn(name = "userID") // 체크하기
    public PersonEntity personEntity;
    
}
