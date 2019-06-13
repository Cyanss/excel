package com.cyan.excel.service.Impl;

import com.cyan.excel.entity.GraduateInfo;
import com.cyan.excel.entity.model.GraduateModel;
import com.cyan.excel.enums.ExcelFileEnum;
import com.cyan.excel.exception.ExcelException;
import com.cyan.excel.repository.GraduateInfoRepository;
import com.cyan.excel.result.ResponseResultEnum;
import com.cyan.excel.service.GraduateService;
import com.cyan.excel.service.handler.ImportHandler;
import com.cyan.excel.utils.ConvertUtils;
import com.cyan.excel.utils.ExcelUtils;
import com.cyan.excel.utils.FileUtils;
import com.cyan.excel.utils.StreamUtils;
import com.cyan.excel.vo.ImportResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther Cyan
 * @create 2019/6/10
 */
@Service
public class GraduateServiceImpl implements GraduateService {
    @Autowired
    private GraduateInfoRepository graduateInfoRepository;

    @Override
    public ImportResultVO importGraduate(MultipartFile excel) {
        List<GraduateModel> graduateModelList;
        ImportResultVO<GraduateModel> resultVO = new ImportResultVO<>();
        try {
            graduateModelList = ExcelUtils.readModelExcel(GraduateModel.class,excel);
        } catch (IOException e) {
            throw new ExcelException(ResponseResultEnum.EXCEL_FILE_READ_FAIL);
        }
        if (graduateModelList == null || graduateModelList.size() == 0) {
            throw new ExcelException(ResponseResultEnum.EXCEL_FILE_IS_EMPTY);
        }
        graduateModelList.forEach(model -> ImportHandler.modelGraduateHandler(model,resultVO,graduateInfoRepository));
        return resultVO;
    }

    @Override
    public void exportGraduate(HttpServletResponse response) {
        String fileName = "毕业信息";
        File model = FileUtils.createFile("毕业信息模板", ExcelFileEnum.MODEL);
        File temp = FileUtils.createFile("毕业信息模板", ExcelFileEnum.TEMP);
        File export = FileUtils.createFile(fileName, ExcelFileEnum.EXPORT);
        if (!model.isFile() || !model.exists()) {
            throw new ExcelException(ResponseResultEnum.MODEL_FILE_NOT_EXIT);
        }
        /** 先复制模板修改成要生成的形式 */
        FileUtils.copyFile(model,temp);
        FileUtils.copyFile(temp,export);
        ExcelUtils.writeModelWorkBook(temp,export,false);
        List<GraduateInfo> graduateInfoList = graduateInfoRepository.findAll();
        List<GraduateModel> graduateModelList = new ArrayList<>();
        for (int index = 0; index < graduateInfoList.size(); index++ ) {
            GraduateInfo graduateInfo = graduateInfoList.get(index);
            GraduateModel graduateModel = ConvertUtils.graduateInfo2GraduateModel(graduateInfo);
            graduateModel.setGraduateYear(graduateInfo.getGraduateYear().toString());
            graduateModel.setIndex(String.valueOf(index+1));
            graduateModelList.add(graduateModel);
        }
        ExcelUtils.writeGraduateModelExport(graduateModelList,temp,export);
        StreamUtils.file2Response(fileName,response,export);
        FileUtils.clearFile(temp);
        FileUtils.clearFile(export);

    }
}
