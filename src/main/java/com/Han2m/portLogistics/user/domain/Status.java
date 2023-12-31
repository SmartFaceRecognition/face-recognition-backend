package com.Han2m.portLogistics.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    private Timestamp enterTime;
    private Timestamp outTime;

    @ManyToOne
    @JoinColumn(name = "wharfId")
    private Wharf wharf;

    @ManyToOne
    @JoinColumn(name = "personId")
    private Person person;

    public void updateEnterTImeTime(Timestamp enterTime){
        this.enterTime = enterTime;
    }
    public void updateOutTime(Timestamp outTime){
        this.outTime = outTime;
    }

}
