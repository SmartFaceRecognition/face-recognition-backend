package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.domain.Wharf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WharfRepository extends JpaRepository<Wharf, Long> {

}

