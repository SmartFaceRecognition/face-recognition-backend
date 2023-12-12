package com.Han2m.portLogistics.user.repository;


import com.Han2m.portLogistics.user.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findByOutTimeIsNull();
    List<Status> findByOutTimeIsNullAndWharfWharfId(Long wharfId);
    @Query("SELECT s FROM Status s WHERE s.wharf.wharfId = :wharfId AND s.person.personId = :personId AND s.outTime IS NULL ORDER BY s.enterTime DESC")
    Status findLastStatusWithNullOutTimeByWharfAndPerson(@Param("wharfId") Long wharfId, @Param("personId") Long personId);
}