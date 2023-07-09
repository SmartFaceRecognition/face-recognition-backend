package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ControlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "controlID")
    private Long id;
    private String openTime;
    private String closeTime;

    public ControlEntity(Long id, String openTime, String closeTime) {
        this.id = id;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    @ManyToOne
    @JoinColumn(name = "userID") // 체크하기
    public PersonEntity personEntity;

    @ManyToOne
    @JoinColumn(name = "wharfID") // 체크하기
    public WharfEntity wharfEntity;
}
