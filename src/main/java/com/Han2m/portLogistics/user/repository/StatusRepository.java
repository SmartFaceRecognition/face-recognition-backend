//package com.Han2m.portLogistics.user.repository;
//
//import com.Han2m.portLogistics.user.entity.Status;
//import com.Han2m.portLogistics.user.entity.Wharf;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//// 현재 부두에 있는 사람들 조회
//@Repository
//public interface StatusRepository extends JpaRepository<Status, Long> {
//    List<Status> findByWharfAndOutTimeIsNull(Wharf wharf);
//    List<Status> findByWharfAndOutTimeIsNull(Wharf wharf);
//}
