package com.Han2m.portLogistics.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

}