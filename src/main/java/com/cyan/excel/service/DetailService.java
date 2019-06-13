package com.cyan.excel.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 详细信息服务
 * @auther Cyan
 * @create 2019/6/10
 */
public interface DetailService {

    /**
     * 导出详细信息
     * @param response
     */
    void exportDetail(HttpServletResponse response);
}
