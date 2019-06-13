package com.cyan.excel.enums;

import lombok.Getter;


@Getter
public enum ExcelFileEnum {
    MODEL(1,"模型"),
    TEMP(2,"临时"),
    EXPORT(3,"导出"),
    ;
    private String content;
    private Integer code;

    ExcelFileEnum(Integer code, String content) {
        this.code = code;
        this.content = content;
    }

    ExcelFileEnum(ExcelFileEnum excelFileEnum) {
        this.code = excelFileEnum.getCode();
        this.content = excelFileEnum.getContent();
    }

}
