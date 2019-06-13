package com.cyan.excel.utils;

import com.cyan.excel.entity.GraduateInfo;
import com.cyan.excel.entity.UserInfo;
import com.cyan.excel.entity.joint.GraduateUserJoint;
import com.cyan.excel.entity.model.DetailModel;
import com.cyan.excel.entity.model.GraduateModel;
import com.cyan.excel.entity.model.UserModel;
import com.cyan.excel.enums.SexTypeEnum;

/**
 * 数据转换工具类
 * @auther Cyan
 * @create 2019/6/10
 */
public class ConvertUtils {

    public static DetailModel joint2DetailModel(GraduateUserJoint jointInfo) {
        DetailModel detailModel = new DetailModel();
        CopyUtils.copyProperties(jointInfo.getUserInfo(), detailModel);
        CopyUtils.copyProperties(jointInfo, detailModel);
        checkSex(jointInfo,detailModel);
        detailModel.setGraduateYear(jointInfo.getGraduateYear().toString());
        return detailModel;
    }

    public static UserModel userInfo2UserModel(UserInfo userInfo) {
        UserModel userModel = new UserModel();
        CopyUtils.copyProperties(userInfo, userModel);
        checkSex(userInfo,userModel);
        return userModel;
    }

    public static GraduateModel graduateInfo2GraduateModel(GraduateInfo graduateInfo) {
        GraduateModel graduateModel = new GraduateModel();
        CopyUtils.copyProperties(graduateInfo, graduateModel);
        return graduateModel;
    }

    /**
     * 性别字段检查
     * @param model
     * @param userInfo
     */
    private static void checkSex(UserInfo userInfo, UserModel model) {
        if (userInfo.getUserSex() != null) {
            model.setUserSex(SexTypeEnum.analysisCode(userInfo.getUserSex()));
        } else {
            model.setUserSex(SexTypeEnum.DEFAULT.getContent());
        }
    }

    /**
     * 性别字段检查
     * @param model
     * @param jointInfo
     */
    private static void checkSex(GraduateUserJoint jointInfo, DetailModel model) {
        if (jointInfo.getUserInfo().getUserSex() != null) {
            model.setUserSex(SexTypeEnum.analysisCode(jointInfo.getUserInfo().getUserSex()));
        } else {
            model.setUserSex(SexTypeEnum.DEFAULT.getContent());
        }
    }
}
