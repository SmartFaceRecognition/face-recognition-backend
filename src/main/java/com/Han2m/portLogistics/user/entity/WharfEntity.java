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

    // Test용 부두 3개
    public String place1;
    public String place2;
    public String place3;

    public WharfEntity(Long id, String place1, String place2, String place3) {
        this.id = id;
        this.place1 = place1;
        this.place2 = place2;
        this.place3 = place3;
    }

    @ManyToOne
    @JoinColumn(name = "userID") // 체크하기
    public PersonEntity personEntity;
    
}
