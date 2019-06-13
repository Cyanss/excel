package com.cyan.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.cyan.excel.entity.GraduateInfo;

import java.util.List;

public interface GraduateInfoRepository extends JpaRepository<GraduateInfo, Integer>, JpaSpecificationExecutor<GraduateInfo> {
    boolean existsByUserId(String userId);
    List<GraduateInfo> findAllByUserId(String userId);
}