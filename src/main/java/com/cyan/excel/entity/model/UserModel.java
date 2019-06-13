package com.cyan.excel.entity.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 个人信息excel模板对应model
 * @auther Cyan
 * @create 2019/6/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserModel extends BaseRowModel {
    /** 从第几行开始写入数据 */
    @Ignore
    private Integer startRow = 1;

    @ExcelProperty(value = "序号" ,index = 0)
    private String index;

    @ExcelProperty(value = "姓名" ,index = 1)
    private String userName;

    @ExcelProperty(value = "性别" ,index = 2)
    private String userSex;

    @ExcelProperty(value = "出生日期" ,index = 3)
    private String userBorn;

    @ExcelProperty(value = "身份证号" ,index = 4)
    private String userId;

    @ExcelProperty(value = "手机号" ,index = 5)
    private String userPhone;

    @ExcelProperty(value = "邮箱" ,index = 6)
    private String userEmail;

    @ExcelProperty(value = "QQ" ,index = 7)
    private String userQQ;
}
