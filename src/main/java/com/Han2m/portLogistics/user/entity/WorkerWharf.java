package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WorkerWharf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workerWharfId;

    public WorkerWharf(Worker worker, Wharf wharf) {
        this.worker = worker;
        this.wharf = wharf;
    }

    @ManyToOne
    @JoinColumn(name = "workerId")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "wharfId")
    private Wharf wharf;
}
