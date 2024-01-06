package com.Han2m.portLogistics.admin.domain;

import com.Han2m.portLogistics.user.domain.Worker;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @OneToOne(mappedBy = "account", optional = false)
    private Worker worker;

    public void editPassword(String password) {
        this.password = password;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}