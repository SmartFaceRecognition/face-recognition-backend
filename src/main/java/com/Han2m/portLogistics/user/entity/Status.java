package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statusID")
    private Long id;
    private String enterTime;
    private String outTime;

    public Status(Long id, String enterTime, String outTime) {
        this.id = id;
        this.enterTime = enterTime;
        this.outTime = outTime;
    }

    @ManyToOne
    @JoinColumn(name = "personID")
    private Person person;

}
