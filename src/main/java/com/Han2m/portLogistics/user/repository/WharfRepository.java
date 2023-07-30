package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.Person;
import com.Han2m.portLogistics.user.entity.Wharf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WharfRepository extends JpaRepository<Wharf, Long> {

    // 사람 ID를 이용하여 Wharf 찾기
    @Query("SELECT pw.wharf FROM PersonWharf pw WHERE pw.person.id = :personId")
    List<Wharf> findByPersonId(@Param("personId") Long personId);

    // 부두명을 기반으로 해당 부두에 있는 모든 사람 찾기
    @Query("SELECT pw.person FROM PersonWharf pw WHERE pw.wharf.place = :place")
    List<Person> findPersonsByPlace(@Param("place") String place);

    @Query("SELECT pw.wharf FROM PersonWharf pw WHERE pw.person.id = :personId")
    List<Wharf> findByPersonWharfListPersonId(@Param("personId") Long personId);

    List<Wharf> findByPlace(String place);

    // 중복체크 메소드 -- 삭제될 수도 있음
    boolean existsByPlace(String place);
}
