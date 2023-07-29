package com.Han2m.portLogistics.user.repository;

import com.Han2m.portLogistics.user.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

// 인원 face 파일 저장
public interface FileRepository extends JpaRepository<File, Long> {

}