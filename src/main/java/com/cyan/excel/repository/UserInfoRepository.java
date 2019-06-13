package com.cyan.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.cyan.excel.entity.UserInfo;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, String>, JpaSpecificationExecutor<UserInfo> {

    List<UserInfo> findAllByUserSex(Integer sexCode);

}