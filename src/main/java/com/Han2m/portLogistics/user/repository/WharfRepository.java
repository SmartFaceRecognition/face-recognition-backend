package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.PersonEntity;
import com.Han2m.portLogistics.user.entity.WharfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WharfRepository extends JpaRepository<WharfEntity, Long> {
    @Query("SELECT w FROM WharfEntity w INNER JOIN w.userWharfEntityList u WHERE u.personEntity.id = :userID")
    List<PersonEntity> findByPersonId(@Param("userID") Long userID);

    @Query("SELECT w FROM WharfEntity w WHERE w.place = :place")
    List<WharfEntity> findByPlace(@Param("place") String place);
}
