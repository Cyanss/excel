package com.cyan.excel.result;


public class ResponseResultUtils {
    private final static Integer SUCCESS_STATUS= 200;

    public static ResponseResult success(Object object){
        ResponseResult<Object> responseResult = new ResponseResult<>();
        responseResult.setData(object);
        responseResult.setStatus(SUCCESS_STATUS);
        responseResult.setMessage("成功");
        return responseResult;
    }

    public static ResponseResult success(String message, Object object){
        ResponseResult<Object> responseResult = new ResponseResult<>();
        responseResult.setData(object);
        responseResult.setStatus(SUCCESS_STATUS);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult success() {
        return success(null);
    }

    public static ResponseResult success(String message) {
        return success(message,null);
    }

    public static ResponseResult error(Integer status, String msg) {
        ResponseResult responseResult =new ResponseResult();
        responseResult.setStatus(status);
        responseResult.setMessage(msg);
        return responseResult;
    }

    public static ResponseResult error(ResponseResultEnum responseResultEnum) {
        ResponseResult responseResult =new ResponseResult();
        responseResult.setStatus(responseResultEnum.getStatus());
        responseResult.setMessage(responseResultEnum.getMessage());
        return responseResult;
    }

    public static ResponseResult error(ResponseResultEnum responseResultEnum,Object object) {
        ResponseResult<Object> responseResult =new ResponseResult<>();
        responseResult.setData(object);
        responseResult.setStatus(responseResultEnum.getStatus());
        responseResult.setMessage(responseResultEnum.getMessage());
        return responseResult;
    }
}
