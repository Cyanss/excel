package com.cyan.excel.entity.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 毕业信息excel模板对应model
 * @auther Cyan
 * @create 2019/6/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GraduateModel extends BaseRowModel {
    /** 从第几行开始写入数据 */
    @Ignore
    private Integer startRow = 1;

    @ExcelProperty(value = "序号", index = 0)
    private String index;

    @ExcelProperty(value = "身份证号", index = 1)
    private String userId;

    @ExcelProperty(value = "毕业院校", index = 2)
    private String graduateSchool;

    @ExcelProperty(value = "院系", index = 3)
    private String graduateDepartment;

    @ExcelProperty(value = "专业", index = 4)
    private String graduateMajor;

    @ExcelProperty(value = "毕业时间", index = 5)
    private String graduateTime;

    @ExcelProperty(value = "学制", index = 6)
    private String graduateYear;

    @ExcelProperty(value = "学历", index = 7)
    private String graduateDegree;

    @ExcelProperty(value = "备注", index = 8)
    private String graduateRemark;

}
