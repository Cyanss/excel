package com.cyan.excel.service.Impl;

import com.cyan.excel.entity.UserInfo;
import com.cyan.excel.entity.model.UserModel;
import com.cyan.excel.enums.ExcelFileEnum;
import com.cyan.excel.enums.SexTypeEnum;
import com.cyan.excel.exception.ExcelException;
import com.cyan.excel.repository.UserInfoRepository;
import com.cyan.excel.result.ResponseResultEnum;
import com.cyan.excel.service.UserService;
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
import java.util.*;

/**
 * @auther Cyan
 * @create 2019/6/10
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public ImportResultVO importUser(MultipartFile excel) {
        List<UserModel> userModelList;
        ImportResultVO<UserModel> resultVO = new ImportResultVO<>();
        try {
            userModelList = ExcelUtils.readModelExcel(UserModel.class,excel);
        } catch (IOException e) {
            throw new ExcelException(ResponseResultEnum.EXCEL_FILE_READ_FAIL);
        }
        if (userModelList == null || userModelList.size() == 0) {
            throw new ExcelException(ResponseResultEnum.EXCEL_FILE_IS_EMPTY);
        }
        userModelList.forEach(model -> ImportHandler.modelUserHandler(model,resultVO,userInfoRepository));
        return resultVO;
    }

    @Override
    public void exportUser(HttpServletResponse response) {
        String fileName = "个人信息";
        File model = FileUtils.createFile("个人信息模板", ExcelFileEnum.MODEL);
        File temp = FileUtils.createFile("个人信息模板", ExcelFileEnum.TEMP);
        File export = FileUtils.createFile(fileName, ExcelFileEnum.EXPORT);
        if (!model.isFile() || !model.exists()) {
            throw new ExcelException(ResponseResultEnum.MODEL_FILE_NOT_EXIT);
        }
        /** 先复制模板修改成要生成的形式 */
        FileUtils.copyFile(model,temp);
        FileUtils.copyFile(temp,export);
        ExcelUtils.writeModelWorkBook(temp,export,false);
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        List<UserModel> userModelList = new ArrayList<>();
        for (int index = 0; index < userInfoList.size(); index++ ) {
            UserInfo userInfo = userInfoList.get(index);
            UserModel userModel = ConvertUtils.userInfo2UserModel(userInfo);
            userModel.setIndex(String.valueOf(index+1));
            userModelList.add(userModel);
        }
        ExcelUtils.writeUserModelExport(userModelList,temp,export);
        StreamUtils.file2Response(fileName,response,export);
        FileUtils.clearFile(temp);
        FileUtils.clearFile(export);

    }

    @Override
    public void exportSex(HttpServletResponse response) {
        String fileName = "分类信息";
        File model = FileUtils.createFile("分类信息模板", ExcelFileEnum.MODEL);
        File temp = FileUtils.createFile("分类信息模板", ExcelFileEnum.TEMP);
        File export = FileUtils.createFile(fileName, ExcelFileEnum.EXPORT);
        if (!model.isFile() || !model.exists()) {
            throw new ExcelException(ResponseResultEnum.MODEL_FILE_NOT_EXIT);
        }
        /** 先复制模板修改成要生成的形式 */
        FileUtils.copyFile(model,temp);
        FileUtils.copyFile(temp,export);
        List<SexTypeEnum> sexTypeEnumList = Arrays.asList(SexTypeEnum.values());
        List<Map<String,String>> nameList = new ArrayList<>();
        List<List<UserInfo>> usersList = new ArrayList<>();
        /** 做一个示例 按性别导出 */
        sexTypeEnumList.forEach(sexTypeEnum -> {
            List<UserInfo> userList = userInfoRepository.findAllByUserSex(sexTypeEnum.getCode());
            if (userList.size() > 0) {
                Map<String,String> nameMap = new HashMap<>();
                nameMap.put("sex",sexTypeEnum.getContent());
                nameList.add(nameMap);
                usersList.add(userList);
            }
        });
        ExcelUtils.writeModelCloneSheet(temp,export,nameList);
        FileUtils.copyFile(export,temp);
        ExcelUtils.writeUserExport(usersList,temp,export);
        StreamUtils.file2Response(fileName,response,export);
        FileUtils.clearFile(temp);
        FileUtils.clearFile(export);

    }
}
