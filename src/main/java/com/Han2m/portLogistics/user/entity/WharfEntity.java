package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "wharfEntity")
    private List<ControlEntity> controlEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "wharfEntity")
    private List<User_Wharf_Entity> userWharfEntityList = new ArrayList<>();

    
}
