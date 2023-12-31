package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    @Query("SELECT w FROM Worker w JOIN FETCH w.permissionList p JOIN FETCH p.wharf")
    List<Worker> findAllWorkerWithWharf();

    @Query("SELECT w FROM Worker w JOIN FETCH w.permissionList p JOIN FETCH p.wharf WHERE w.personId = :personId")
    Optional<Worker> findById(@Param("personId") Long personId);

}