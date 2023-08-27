package com.Han2m.portLogistics.user.entity;

import com.Han2m.portLogistics.user.entity.Worker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Signup{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberId;
    private String password;

    public void updateInfo(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    @OneToOne(mappedBy = "signup")
    private Worker worker;
}
