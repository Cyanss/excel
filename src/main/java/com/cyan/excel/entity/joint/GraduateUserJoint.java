package com.cyan.excel.entity.joint;

import com.cyan.excel.entity.UserInfo;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 数据库联表Join对应实体
 * @auther Cyan
 * @create 2019/6/10
 */

@Data
@Entity
@Table(name = "graduate_info")
public class GraduateUserJoint implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 毕业信息编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, name = "graduate_id", nullable = false)
    private Integer graduateId;

    /**
     * 用户编号
     */
    @JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL)
    private UserInfo userInfo;

    /**
     * 毕业院校名称
     */
    @Column(name = "graduate_school", nullable = false)
    private String graduateSchool;

    /**
     * 毕业院系
     */
    @Column(name = "graduate_department", nullable = false)
    private String graduateDepartment;

    /**
     * 毕业专业
     */
    @Column(name = "graduate_major", nullable = false)
    private String graduateMajor;

    /**
     * 毕业时间
     */
    @Column(name = "graduate_time", nullable = false)
    private String graduateTime;

    /**
     * 毕业学制
     */
    @Column(name = "graduate_year", nullable = false)
    private Integer graduateYear;

    /**
     * 专业
     */
    @Column(name = "graduate_degree", nullable = false)
    private String graduateDegree;

    /**
     * 备注
     */
    @Column(name = "graduate_remark")
    private String graduateRemark;

    /**
     * 更新时间
     */
    @Column(name = "update_time", nullable = false)
    private Timestamp updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

}
