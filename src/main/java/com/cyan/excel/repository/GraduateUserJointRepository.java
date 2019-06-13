package com.cyan.excel.repository;

import com.cyan.excel.entity.joint.GraduateUserJoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @auther Cyan
 * @create 2019/6/12
 */
public interface GraduateUserJointRepository extends JpaRepository<GraduateUserJoint, Integer>, JpaSpecificationExecutor<GraduateUserJoint> {
}
