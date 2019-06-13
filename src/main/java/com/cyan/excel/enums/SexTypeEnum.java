package com.cyan.excel.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ProjectName: posreview
 * @Package: com.zhitu.posreview.enums.user
 * @ClassName: SexTypeEnum
 * @Author: admin Cyan
 * @Description: 用户性别类型枚举
 * @Date: 2019/5/13 16:23
 * @Version: 1.0.0
 */
@Getter
public enum SexTypeEnum {
    DEFAULT(0,""),
    SEX_MALE(1,"男"),
    SEX_FEMALE(2,"女")
    ;
    private Integer code;
    private String content;

    SexTypeEnum(Integer code, String content) {
        this.code = code;
        this.content = content;
    }

    public static String analysisCode(Integer code) {
        List<SexTypeEnum> enumList = Arrays.asList(SexTypeEnum.values());
        List<SexTypeEnum> filterEnumList = enumList.stream().filter(enums -> enums.getCode().equals(code)).collect(Collectors.toList());
        if (filterEnumList.size() > 0) {
            return filterEnumList.get(0).getContent();
        } else {
            return DEFAULT.getContent();
        }
    }

    public static Integer analysisContent(String content) {
        List<SexTypeEnum> enumList = Arrays.asList(SexTypeEnum.values());
        List<SexTypeEnum> filterEnumList = enumList.stream().filter(enums -> enums.getContent().equals(content)).collect(Collectors.toList());
        if (filterEnumList.size() > 0) {
            return filterEnumList.get(0).getCode();
        } else {
            return DEFAULT.getCode();
        }
    }
}
