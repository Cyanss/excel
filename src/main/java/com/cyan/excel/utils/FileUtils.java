package com.cyan.excel.utils;

import com.cyan.excel.enums.ExcelFileEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 文件工具类
 * @auther Cyan
 * @create 2019/6/10
 */
public class FileUtils {

    public static File createFile(String name, ExcelFileEnum excelFileEnum) {
        File file = null;
        switch (excelFileEnum) {
            case MODEL:
                file = new File(PathUtils.getModelPath()+"/"+ name +".xlsx");
                break;
            case TEMP:
                file = new File(PathUtils.getTempPath()+"/"+ name +".xlsx");
                break;
            case EXPORT:
                file = new File(PathUtils.getExportPath()+"/"+ name +".xlsx");
                break;
        }

        return file;
    }

    public static void copyFile(File srcFile, File targetFile) {
        FileChannel input;
        FileChannel output;
        try {
            input = new FileInputStream(srcFile).getChannel();
            output = new FileOutputStream(targetFile).getChannel();
            output.transferFrom(input, 0, input.size());
            input.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(String srcPath, String targetPath) {
        File srcFile = new File(srcPath);
        File targetFile = new File(targetPath);
        copyFile(srcFile,targetFile);
    }



    public static File createFile(File file) {
        if(!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    public static File createFile(String filePath){
        File file = new File(filePath);
        if(!file.exists()) {
            file = new File("");
        }
        return file;
    }

    public static boolean clearFile(File file) {
        if (file.exists() && file.isFile()){
            return file.delete();
        } else {
            return true;
        }
    }


    public static boolean clearAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        String[] tempList = file.list();
        File temp;
        for (int i = 0;  tempList != null && i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                boolean delete = temp.delete();
            }
            if (temp.isDirectory()) {
                //先删除文件夹里面的文件
                clearAllFile(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }
}
