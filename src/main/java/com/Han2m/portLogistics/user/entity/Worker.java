package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Worker extends Person{

    private String faceUrl;
    private String fingerprint;
    private String position; // 직급

    public Worker(Long id, String nationality, String name, Boolean isWorker, Boolean sex, String birth, String phone, String faceUrl, String fingerprint, String position) {
        super(id, nationality, name, Boolean.TRUE, sex, birth, phone);
        this.faceUrl = faceUrl;
        this.fingerprint = fingerprint;
        this.position = position;
    }
}
