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

    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
    private List<String> roles;
}