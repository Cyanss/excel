package com.cyan.excel.service;

import com.cyan.excel.vo.ImportResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户信息服务
 * @auther Cyan
 * @create 2019/6/10
 */
public interface UserService {
    /**
     * 导入用户信息
     * @param excel
     */
    ImportResultVO importUser(MultipartFile excel);

    /**
     * 导出用户信息
     * @param response
     */
    void exportUser(HttpServletResponse response);

    /**
     * 按性别分别导出用户信息
     * @param response
     */
    void exportSex(HttpServletResponse response);
}
