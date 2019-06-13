package com.cyan.excel.controller;

import com.cyan.excel.result.ResponseResult;
import com.cyan.excel.result.ResponseResultUtils;
import com.cyan.excel.service.DetailService;
import com.cyan.excel.service.GraduateService;
import com.cyan.excel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @auther Cyan
 * @create 2019/6/10
 */
@RestController
@CrossOrigin
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private UserService userService;

    @Autowired
    private DetailService detailService;

    @Autowired
    private GraduateService graduateService;
    /**
     * 导入个人Excel文件
     * @param excel
     * @return
     */
    @PostMapping("/importUser")
    public ResponseResult importUser(@RequestParam("excel") MultipartFile excel) {
        return ResponseResultUtils.success("导入成功",userService.importUser(excel));
    }


    /**
     * 导出个人Excel文件
     * @param response
     */
    @GetMapping("/exportUser")
    public void exportUser(HttpServletResponse response) {
        userService.exportUser(response);
    }


    /**
     * 导入个人Excel文件
     * @param excel
     * @return
     */
    @PostMapping("/importGraduate")
    public ResponseResult importGraduate(@RequestParam("excel") MultipartFile excel) {
        return ResponseResultUtils.success("导入成功",graduateService.importGraduate(excel));
    }


    /**
     * 导出个人Excel文件
     * @param response
     */
    @GetMapping("/exportGraduate")
    public void exportGraduate(HttpServletResponse response) {
        graduateService.exportGraduate(response);
    }

    /**
     * 按性别分类导出个人Excel文件
     * @param response
     */
    @GetMapping("/exportSex")
    public void exportSex(HttpServletResponse response) {
        userService.exportSex(response);
    }

    /**
     * 导出详细信息Excel文件
     * @param response
     */
    @GetMapping("/exportDetail")
    public void exportDetail(HttpServletResponse response) {
        detailService.exportDetail(response);
    }
}
