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

    List<Wharf> findByUserWharfListPersonId(Long personId);
    List<Wharf> findByGuestName(String guestName);

    List<Wharf> findByPlace(String place);
    boolean existsByPlace(String place); // 중복체크 여부는 나중에 사라질수도 있나 ? // 자꾸 부두가 중복으로 처리돼서 넣어놓음

}
