package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.Person;
import com.Han2m.portLogistics.user.entity.Wharf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WharfRepository extends JpaRepository<Wharf, Long> {
    // 부두 검색
    List<Wharf> findByPlace(String place);

}

