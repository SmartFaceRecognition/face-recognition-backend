package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workerID")
    private Long id;
    private String faceUrl;
    private String fingerprint;
    private String position;

    public Worker(Long id, String faceUrl, String fingerprint, String position) {
        this.id = id;
        this.faceUrl = faceUrl;
        this.fingerprint = fingerprint;
        this.position = position;
    }
}
