package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
