package com.Han2m.portLogistics.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("worker")
public class Worker extends Person{

    private String faceUrl;
    private String fingerprint;
    private String position; // 직급

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<Status> statusList = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<Control> controlList = new ArrayList<>();

    // 여기 상속관계를 추가해야될 수도 있음
    @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WorkerWharf> workerWharfList = new ArrayList<>();

    public List<String> getWharfPlaces() {
        return workerWharfList.stream()
                .map(WorkerWharf::getWharf)
                .map(Wharf::getPlace)
                .collect(Collectors.toList());
    }

}
