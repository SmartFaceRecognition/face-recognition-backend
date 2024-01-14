package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.domain.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Query("select g from Guest g join fetch g.permissionList p join fetch p.wharf WHERE (:name IS NULL OR g.name LIKE %:name%)")
    Page<Guest> findAllGuestWithWharf(String name, Pageable pageable);

    @Query("select g from Guest g join fetch g.permissionList p join fetch g.worker join fetch p.wharf where g.personId = :personId")
    Optional<Guest> findById(Long personId);
}
