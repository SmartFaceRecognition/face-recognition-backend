package com.Han2m.portLogistics.user.repository;


import com.Han2m.portLogistics.user.domain.Person;
import com.Han2m.portLogistics.user.domain.Status;
import com.Han2m.portLogistics.user.domain.Wharf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findByOutTimeIsNull();
    List<Status> findByOutTimeIsNullAndWharfWharfId(Long wharfId);
    @Query("SELECT s FROM Status s WHERE s.person = :person AND s.wharf = :wharf AND s.outTime IS NULL")
    Optional<Status> findByPersonAndWharf(Person person, Wharf wharf);
}