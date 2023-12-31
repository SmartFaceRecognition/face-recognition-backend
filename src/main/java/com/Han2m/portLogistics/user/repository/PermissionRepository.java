package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.domain.Permission;
import com.Han2m.portLogistics.user.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    @Modifying
    @Query("DELETE FROM Permission p WHERE p.person = :person")
    void deleteByPerson(@Param("person") Person person);
}
