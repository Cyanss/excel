package com.cyan.excel.service;

import com.cyan.excel.vo.ImportResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @auther Cyan
 * @create 2019/6/10
 */
public interface GraduateService {

    /**
     * 导入毕业信息
     * @param excel
     */
    ImportResultVO importGraduate(MultipartFile excel);

    /**
     * 导出毕业信息
     * @param response
     */
    void exportGraduate(HttpServletResponse response);
}
