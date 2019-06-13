package com.cyan.excel.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "graduate_info")
public class GraduateInfo implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 毕业信息编号
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(insertable = false, name = "graduate_id", nullable = false)
  private Integer graduateId;

  /**
   * 用户身份证
   */
  @Column(name = "user_id", nullable = false)
  private String userId;

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