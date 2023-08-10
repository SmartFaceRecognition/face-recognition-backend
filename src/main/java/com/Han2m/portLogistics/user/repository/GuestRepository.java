package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByName (String name);
}
