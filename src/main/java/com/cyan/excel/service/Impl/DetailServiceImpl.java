package com.cyan.excel.service.Impl;

import com.cyan.excel.entity.joint.GraduateUserJoint;
import com.cyan.excel.entity.model.DetailModel;
import com.cyan.excel.enums.ExcelFileEnum;
import com.cyan.excel.exception.ExcelException;
import com.cyan.excel.repository.GraduateUserJointRepository;
import com.cyan.excel.result.ResponseResultEnum;
import com.cyan.excel.service.DetailService;
import com.cyan.excel.utils.ConvertUtils;
import com.cyan.excel.utils.ExcelUtils;
import com.cyan.excel.utils.FileUtils;
import com.cyan.excel.utils.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther Cyan
 * @create 2019/6/10
 */
@Service
public class DetailServiceImpl implements DetailService {
    @Autowired
    private GraduateUserJointRepository jointRepository;

    @Override
    public void exportDetail(HttpServletResponse response) {
        String fileName = "详细信息";
        File model = FileUtils.createFile("详细信息模板", ExcelFileEnum.MODEL);
        File temp = FileUtils.createFile("详细信息模板", ExcelFileEnum.TEMP);
        File export = FileUtils.createFile(fileName, ExcelFileEnum.EXPORT);
        if (!model.isFile() || !model.exists()) {
            throw new ExcelException(ResponseResultEnum.MODEL_FILE_NOT_EXIT);
        }
        /** 先复制模板修改成要生成的形式 */
        FileUtils.copyFile(model,temp);
        FileUtils.copyFile(temp,export);
        List<GraduateUserJoint> jointList = jointRepository.findAll();
        List<DetailModel> detailModelList = new ArrayList<>();
        for (int index = 0; index < jointList.size(); index++ ) {
            GraduateUserJoint jointInfo = jointList.get(index);
            DetailModel detailModel = ConvertUtils.joint2DetailModel(jointInfo);
            detailModel.setIndex(String.valueOf(index+1));
            detailModelList.add(detailModel);
        }
        ExcelUtils.writeModelWorkBook(temp,export,true);
        ExcelUtils.writeDetailModelExport(detailModelList,temp,export);
        StreamUtils.file2Response(fileName,response,export);
        FileUtils.clearFile(temp);
        FileUtils.clearFile(export);
    }
}
