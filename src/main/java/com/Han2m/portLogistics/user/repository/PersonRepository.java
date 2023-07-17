package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByPosition(String position); // 직원 정보 조회
    List<Person> findByPositionNot(String position); // 손님 정보 조회
}
