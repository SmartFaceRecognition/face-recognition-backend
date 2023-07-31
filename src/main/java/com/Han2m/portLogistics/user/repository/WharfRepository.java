package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.Guest;
import com.Han2m.portLogistics.user.entity.Person;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WharfRepository extends JpaRepository<Wharf, Long> {

    // 부두 검색
    List<Wharf> findByPlace(String place);

    // 부두에 속한 모든 사람 찾기
    @Query("SELECT pw.person FROM PersonWharf pw WHERE pw.wharf.place = :place")
    List<Person> findPersonsByPlace(@Param("place") String place);

    // 부두에 속한 직원 찾기
    @Query("SELECT pw.person FROM PersonWharf pw WHERE pw.wharf.place = :place AND TYPE(pw.person) = Worker")
    List<Person> findWorkersByPlace(@Param("place") String place);

    // 부두에 속한 손님 찾기
    @Query("SELECT pw.person FROM PersonWharf pw WHERE pw.wharf.place = :place AND TYPE(pw.person) = Guest")
    List<Person> findGuestsByPlace(@Param("place") String place);
}

