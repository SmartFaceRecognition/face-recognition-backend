package com.Han2m.portLogistics.user.repository;


import com.Han2m.portLogistics.user.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Person, Long> {
}