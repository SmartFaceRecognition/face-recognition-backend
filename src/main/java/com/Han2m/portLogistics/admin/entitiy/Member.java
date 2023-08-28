package com.Han2m.portLogistics.admin.entitiy;

import com.Han2m.portLogistics.user.entity.Worker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberId;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
    private List<String> roles;

    public void updateInfo(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    @OneToOne(mappedBy = "member")
    private Worker worker;

}