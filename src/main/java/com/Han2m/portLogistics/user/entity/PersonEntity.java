package com.Han2m.portLogistics.user.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PersonEntity {

    // 국적, 성별, 생년월일 등은 @Column(updatable = false)이 필요할 수도 있겠다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private Long id;
    private String nationality;
    private String name;
    private String sex;
    private String birth;
    private String phone;
    private String position;
    private String faceUrl;
    private String fingerprint;


    public PersonEntity(Long id, String nationality, String name, String sex, String birth, String phoneNumber, String position, String faceUrl, String fingerprint) {
        this.id = id;
        this.nationality = nationality;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.phone = phoneNumber;
        this.position = position;
        this.faceUrl = faceUrl;
        this.fingerprint = fingerprint;
    }

    // mapped by는 저쪽이 주인이라는 걸 가리킴
    @OneToMany(mappedBy = "personEntity")
    private List<StatusEntity> statusEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "personEntity")
    private List<ControlEntity> controlEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "personEntity")
    private List<User_Wharf_Entity> userWharfEntityList = new ArrayList<>();
}
