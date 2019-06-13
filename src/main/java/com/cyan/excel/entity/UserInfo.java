package com.cyan.excel.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_info")
@Data
public class UserInfo implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 用户身份证
   */
  @Id
  @Column(insertable = false, name = "user_id", nullable = false)
  private String userId;

  /**
   * 用户名称
   */
  @Column(name = "user_name", nullable = false)
  private String userName;

  /**
   * 用户性别，1：男，2：女
   */
  @Column(name = "user_sex", nullable = false)
  private Integer userSex;

  /**
   * 出生日期
   */
  @Column(name = "user_born", nullable = false)
  private String userBorn;

  /**
   * 用户手机号
   */
  @Column(name = "user_phone", nullable = false)
  private String userPhone;

  /**
   * 用户邮箱
   */
  @Column(name = "user_email")
  private String userEmail;

  /**
   * 用户QQ号
   */
  @Column(name = "user_qq")
  private String userQQ;

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