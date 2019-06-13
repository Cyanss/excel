package com.cyan.excel.result;

import lombok.Data;

/**
 * 返回体
 * @auther Cyan
 * @create 2019/6/10
 */

@Data
public class ResponseResult<T> {
    /** 错误码 */
    private Integer status;
    /** 信息 */
    private String message;
    /** 内容 */
    private T data;
}
