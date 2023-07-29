package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.Guest;
import com.Han2m.portLogistics.user.entity.Person;
import com.Han2m.portLogistics.user.entity.Status;
import com.Han2m.portLogistics.user.entity.Wharf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WharfRepository extends JpaRepository<Wharf, Long> {

    // personWharfList의 Person ID를 이용하여 Wharf 찾기
    List<Wharf> findByPersonWharfListPersonId(Long personId);

    // 부두 명을 기반으로 해당 부두에있는 모든사람 찾기
    @Query("SELECT pw.person FROM PersonWharf pw WHERE pw.wharf.place = :place")
    List<Person> findCurrentPersonByWharf(@Param("place") String place);

    // 중복체크 메소드 -- 삭제될수도
    boolean existsByPlace(String place);
}

