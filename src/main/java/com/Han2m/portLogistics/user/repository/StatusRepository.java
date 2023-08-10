package com.Han2m.portLogistics.user.repository;


import com.Han2m.portLogistics.user.entity.Person;
import com.Han2m.portLogistics.user.entity.Status;
import com.amazonaws.services.s3.model.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Stack;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findByOutTimeIsNull();
    List<Status> findByOutTimeIsNullAndWharfWharfId(Long wharfId);
    @Query("SELECT s FROM Status s WHERE s.wharf.id = :wharfId AND s.worker.id = :workerId AND s.outTime IS NULL ORDER BY s.enterTime DESC")
    Status findLastStatusWithNullOutTimeByWharfAndWorker(@Param("wharfId") Long wharfId, @Param("workerId") Long workerId);
}