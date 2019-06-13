package com.cyan.excel.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ImportResultVO<T> {
    /** 成功数量 */
    private Integer success = 0;
    /** 失败数量 */
    private Integer failure = 0;
    /** 重复数量 */
    private Integer repeat = 0;
    /** 失败数据 */
    private List<T> failureList;
    /** 重复数据 */
    private List<T> repeatList;

    public List<T> initFailureList() {
        this.failureList = new ArrayList<>();
        return this.failureList;
    }

    public List<T> initRepeatList() {
        this.repeatList = new ArrayList<>();
        return this.repeatList;
    }

    public synchronized void increaseSuccess() {
        this.success++;
    }

    public synchronized void increaseFailure() {
        this.failure++;
    }

    public synchronized void increaseRepeat() {
        this.repeat++;
    }
}
