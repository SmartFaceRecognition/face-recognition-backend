package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.WorkerWharf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerWharfRepository extends JpaRepository<WorkerWharf, Long> {
}