package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByName (String name);

    void deleteByPersonId(Long id);
}
