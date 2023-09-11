package com.Han2m.portLogistics.admin.entitiy;

import com.Han2m.portLogistics.user.entity.Worker;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
    private List<String> roles;

    public void updateInfo(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }

    @OneToOne(mappedBy = "account")
    private Worker worker;

}