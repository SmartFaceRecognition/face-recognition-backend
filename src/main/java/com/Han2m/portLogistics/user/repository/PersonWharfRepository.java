package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.PersonWharf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonWharfRepository extends JpaRepository<PersonWharf, Long> {
}
