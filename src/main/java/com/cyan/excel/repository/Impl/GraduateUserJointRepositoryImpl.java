package com.cyan.excel.repository.Impl;

import com.cyan.excel.entity.joint.GraduateUserJoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

/**
 * @auther Cyan
 * @create 2019/6/10
 */
@Service
public class GraduateUserJointRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 动态SQL查询数据分页
     * @param whereSql
     * @param pageable
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page<GraduateUserJoint> findAllByGraduateUserJointWithWhereSql(String whereSql, Pageable pageable) {
        /** 原生SQL语句结合外部动态构建的whereSql实现动态SQL拼接(在这里拼也不是不可以) */
        /** 联表Join时根据需求确定 left/right/inner */
        /** whereSql 中最少要包含 1=1 类似的逻辑表达式 防止SQL报错 */
        String dataSql = "select * from graduate_info g inner join user_info u on u.user_id = g.user_id where " + whereSql;
        String countSql = "select count(1) from graduate_info g inner join user_info u on u.user_id = g.user_id where " + whereSql;
        Query dataQuery = entityManager.createNativeQuery(dataSql, GraduateUserJoint.class);

        Query countQuery = entityManager.createNativeQuery(countSql);
        /** 分页供能实现 */
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());
        /** 分页总数统计 */
        BigInteger count = (BigInteger) countQuery.getSingleResult();
        long total = count.longValue();
        /** 分页数据 */
        List<GraduateUserJoint> graduateUserJointList = total > pageable.getOffset() ? dataQuery.getResultList() : Collections.<GraduateUserJoint> emptyList();
        return new PageImpl<>(graduateUserJointList, pageable, total);
    }

    /**
     * 查询数据分页
     * @param pageable
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page<GraduateUserJoint> findAllByGraduateUserJoint(Pageable pageable) {

        Query dataQuery = entityManager.createNativeQuery("select * from graduate_info g inner join user_info u on u.user_id = g.user_id", GraduateUserJoint.class);
        Query countQuery = entityManager.createNativeQuery("select count(1) from graduate_info g inner join user_info u on u.user_id = g.user_id");

        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());

        BigInteger count = (BigInteger) countQuery.getSingleResult();
        long total = count.longValue();
        List<GraduateUserJoint> graduateUserJointList = total > pageable.getOffset() ? dataQuery.getResultList() : Collections.<GraduateUserJoint> emptyList();
        return new PageImpl<>(graduateUserJointList, pageable, total);
    }

    /**
     * 动态SQL查询数据
     * @param whereSql
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<GraduateUserJoint> findAllByGraduateUserJointWithWhereSql(String whereSql) {
        Query dataQuery = entityManager.createNativeQuery("select * from graduate_info g inner join user_info u on u.user_id = g.user_id where " + whereSql, GraduateUserJoint.class);
        return dataQuery.getResultList();
    }

    /**
     * 查询所有信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<GraduateUserJoint> findAllByGraduateUserJoint() {
        Query dataQuery = entityManager.createNativeQuery("select * from graduate_info g inner join user_info u on u.user_id = g.user_id", GraduateUserJoint.class);
        return (List<GraduateUserJoint>) dataQuery.getResultList();
    }
}
