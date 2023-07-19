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
    private String wharf;
    private String enterTime;
    private String outTime;

    // entity에는 반드시 public 또는 protected로 된 생성자가 있어야 한다.
    public Status(Long id, String wharf, String enterTime, String outTime) {
        this.id = id;
        this.wharf = wharf;
        this.enterTime = enterTime;
        this.outTime = outTime;
    }

    @ManyToOne
    @JoinColumn(name = "personID")
    private Person person;
}
