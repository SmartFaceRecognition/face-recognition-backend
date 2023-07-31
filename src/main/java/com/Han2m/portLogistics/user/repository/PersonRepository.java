package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.Guest;
import com.Han2m.portLogistics.user.entity.Person;
import com.Han2m.portLogistics.user.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(String name);
}
