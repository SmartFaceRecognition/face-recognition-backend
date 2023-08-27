package com.Han2m.portLogistics.admin.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member{

    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String memberId;
    @Column(nullable = false)
    private String password;
    private String nationality;
    private String name;
    private Boolean sex;
    private String birth;
    private String phone;
    private String faceUrl;
    private String company;
    private String position;

    public void updateInfo(String memberId, String newpassword, String nationality, String name, Boolean sex, String birth, String phone, String faceUrl, String company, String position) {
        this.memberId = memberId;
        this.password = newpassword;
        this.nationality = nationality;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.phone = phone;
        this.faceUrl = faceUrl;
        this.company = company;
        this.position = position;
    }


    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
    private List<String> roles;

}
