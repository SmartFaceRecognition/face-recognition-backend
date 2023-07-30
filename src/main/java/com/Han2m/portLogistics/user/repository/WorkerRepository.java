package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.Guest;
import com.Han2m.portLogistics.user.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    List<Worker> findByWorkerName(String name);
}