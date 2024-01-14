package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.domain.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    @Query("SELECT w FROM Worker w JOIN FETCH w.permissionList p JOIN FETCH p.wharf WHERE (:name IS NULL OR w.name LIKE %:name%)")
    Page<Worker> findAllWorkerWithWharf(String name,Pageable pageable);

    @Query("SELECT w FROM Worker w JOIN FETCH w.permissionList p JOIN FETCH p.wharf WHERE w.personId = :personId")
    Optional<Worker> findByIdWithWharf(@Param("personId") Long personId);
    @Query("SELECT w FROM Worker w JOIN FETCH w.account a WHERE a.accountId = :accountId")
    Optional<Worker> findByAccountId(String accountId);
}