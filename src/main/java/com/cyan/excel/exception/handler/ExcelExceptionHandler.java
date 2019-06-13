package com.cyan.excel.exception.handler;

import com.cyan.excel.exception.ExcelException;
import com.cyan.excel.result.ResponseResult;
import com.cyan.excel.result.ResponseResultUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理（抛到前台）
 * @auther Cyan
 * @create 2019/6/10
 */
@ControllerAdvice
public class ExcelExceptionHandler {
    @ExceptionHandler(value = ExcelException.class)
    @ResponseBody
    public ResponseResult handlerCouponException(ExcelException exception ) {
        return ResponseResultUtils.error(exception.getStatus(),exception.getMessage());
    }
}
