package com.cyan.excel.exception;

import com.cyan.excel.result.ResponseResultEnum;
import lombok.Getter;

/**
 * 全局异常
 * @auther Cyan
 * @create 2019/6/10
 */
@Getter
public class ExcelException extends RuntimeException {

    private Integer status;

    public ExcelException(ResponseResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.status = resultEnum.getStatus();
    }

    public ExcelException(Integer status, String message) {
        super(message);
        this.status = status;
    }
}
