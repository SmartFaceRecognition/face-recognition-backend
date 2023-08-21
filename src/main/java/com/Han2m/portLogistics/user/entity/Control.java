package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Control {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long controlId;
    private String openTime;
    private String closeTime;

    @ManyToOne
    @JoinColumn(name = "workerId")
    private Worker worker;

}
