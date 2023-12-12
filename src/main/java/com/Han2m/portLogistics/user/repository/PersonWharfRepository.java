package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.domain.Person;
import com.Han2m.portLogistics.user.domain.PersonWharf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonWharfRepository extends JpaRepository<PersonWharf,Long> {
    @Modifying
    @Query("DELETE FROM PersonWharf pw WHERE pw.person = :person")
    void deleteByPerson(@Param("person") Person person);
}
