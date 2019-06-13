package com.cyan.excel.utils;


import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.*;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cyan.excel.entity.UserInfo;
import com.cyan.excel.entity.model.DetailModel;
import com.cyan.excel.entity.model.GraduateModel;
import com.cyan.excel.entity.model.UserModel;
import com.cyan.excel.enums.ExcelFileEnum;
import com.cyan.excel.exception.ExcelException;
import com.cyan.excel.listener.ExcelListener;
import com.cyan.excel.result.ResponseResultEnum;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class ExcelUtils {
    /**
     * excel文件读取
     * @param clazz model类型Class对象
     * @param excel excel文件对象
     * @param <T> model类型泛型
     * @return model类型数据集合
     * @throws IOException IO异常
     */
    public static <T extends BaseRowModel> List<T> readModelExcel(Class<T> clazz,MultipartFile excel) throws IOException{
        ExcelListener<T> excelListener = new ExcelListener<>();
        ExcelReader reader = getReader(excel, excelListener);
        reader.getSheets().forEach(sheet -> {
            sheet.setClazz(clazz);
            reader.read(sheet);
        });
        return excelListener.getDataList();
    }

    /**
     * 获取excel文件读取对象
     * @param excel excel文件对象
     * @param excelListener excel事件处理
     * @return excel 读取对象
     * @throws IOException IO异常
     */
    private static ExcelReader getReader(MultipartFile excel, ExcelListener excelListener) throws IOException {
        String filename = excel.getOriginalFilename();
        if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
            throw new ExcelException(ResponseResultEnum.EXCEL_FILE_EXT_ERROR);
        }
        InputStream inputStream = new BufferedInputStream(excel.getInputStream());
        return new ExcelReader(inputStream, null, excelListener, false);
    }

    /**
     * 写入数据到导出文件
     * @param clazz model类型Class对象
     * @param sheetNo sheet序号
     * @param headLineMun head行数
     * @param startRow 开始写入行数
     * @param tableNo table序号
     * @param modelList model数据集合
     * @param temp 临时文件对象
     * @param export 导出文件对象
     * @param <T> model泛型
     */
    public static <T extends BaseRowModel> void writeModelExport(Class<T> clazz, Integer sheetNo, Integer headLineMun,Integer startRow,Integer tableNo,List<T> modelList, File temp, File export){
        try {
            InputStream inputStream = new FileInputStream(temp);
            FileOutputStream OutputStream = new  FileOutputStream(export);
            ExcelWriter writer = EasyExcelFactory.getWriterWithTemp(inputStream, OutputStream, ExcelTypeEnum.XLSX,false);
            writer.write(modelList, initSheet(sheetNo,headLineMun,startRow), initTable(tableNo,clazz));
            writer.finish();
            OutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sheet初始化
     * @param sheetNo sheet序号
     * @param headLineMun head行数
     * @param startRow 开始写入行数
     * @return Sheet对象
     */
    public static Sheet initSheet(Integer sheetNo, Integer headLineMun,Integer startRow) {
        Sheet sheet = new Sheet(sheetNo, headLineMun);
        sheet.setStartRow(startRow);
        return sheet;
    }

    /**
     * Table初始化
     * @param tableNo table序号
     * @param clazz model类型Class对象
     * @param <T> model泛型
     * @return Table对象
     */
    public static <T extends BaseRowModel> Table initTable(Integer tableNo,Class<T> clazz) {
        Table table = new Table(tableNo);
        table.setClazz(clazz);
        TableStyle tableStyle = new TableStyle();
        tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE);
        tableStyle.setTableHeadBackGroundColor(IndexedColors.WHITE);
        Font font = new Font();
        font.setBold(false);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)11);
        tableStyle.setTableContentFont(font);
        table.setTableStyle(tableStyle);
        return table;
    }

    /**
     * 替换模板标记
     * @param export 导出文件对象
     * @param index sheet序号
     * @param rows 单元格行序
     * @param columns 单元格列序
     * @param replaceMap 多个标记集合
     */
    public static void editModelWorkBook(File export,Integer index, Integer rows,Integer columns,Map<String,String> replaceMap) {
        try {
            XSSFWorkbook workBook = new XSSFWorkbook(export);
//            editModelWorkBook(workBook,index,0,0,replaceMap);
            editModelWorkBook(workBook,index,rows,columns,replaceMap);
            workBook.close();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换模板标记
     * @param workBook excel工作册对象
     * @param index sheet序号
     * @param rows 单元格行序
     * @param columns 单元格列序
     * @param replaceMap 多个标记集合
     */
    public static void editModelWorkBook(XSSFWorkbook workBook,Integer index, Integer rows,Integer columns, Map<String,String> replaceMap) {
        XSSFSheet sheet = workBook.getSheetAt(index);
        Row row = sheet.getRow(rows);
        Cell cell = row.getCell(columns);
        String replaceString = cell.toString();
        for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
            replaceString = replaceString.replace(entry.getKey(), entry.getValue());
        }
        cell.setCellValue(replaceString);
        workBook.setSheetName(index,replaceString.replace(":",""));
    }

    /**
     * 模板sheet克隆及命名
     * @param temp 临时文件对象
     * @param export 导出文件对象
     * @param cloneSheetNo 克隆sheet序列
     * @param rows 单元格行序
     * @param columns 单元格列序
     * @param replaceMapList 多个标记集合
     */
    public static void writeModelCloneSheet(File temp, File export, Integer cloneSheetNo, Integer rows,Integer columns, List<Map<String,String>> replaceMapList) {
        try {
            XSSFWorkbook workBook = new XSSFWorkbook(temp);
            OutputStream outputStream = new FileOutputStream(export);
            /** 如果你需要6份相同模板的sheet 那么你只需要克隆5份即可*/
            for (int index = 0;index < replaceMapList.size()-1;index++) {
                /** 克隆模板文件 */
                XSSFSheet sheet = workBook.cloneSheet(cloneSheetNo);
            }
            for (int index = 0;index < replaceMapList.size();index++) {
                editModelWorkBook(workBook,index,rows,columns,replaceMapList.get(index));
            }
            workBook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workBook.close();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * 临时文件写入导出文件
     * @param temp 临时文件对象
     * @param export 导出文件对象
     * @param index sheet序号
     * @param rows 单元格行序
     * @param columns 单元格列序
     * @param isTime 是否有时间标记
     */
    public static void writeModelWorkBook(File temp, File export,Integer index, Integer rows,Integer columns, Boolean isTime) {
        try {
            XSSFWorkbook workBook = new XSSFWorkbook(temp);
            OutputStream outputStream = new FileOutputStream(export);
            if (isTime) {
                writeModelWorkBook(workBook,index,rows,columns);
            }
            workBook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workBook.close();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换时间标记（year,month,day）
     * @param workBook excel工作册对象
     * @param index sheet序号
     * @param rows 单元格行序
     * @param columns 单元格列序
     */
    public static void writeModelWorkBook(XSSFWorkbook workBook,Integer index, Integer rows,Integer columns) {
        XSSFSheet xssfSheet = workBook.getSheetAt(index);
        Row row = xssfSheet.getRow(rows);
        Cell cell = row.getCell(columns);
        String cellString = cell.toString();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String yearString = cellString.replace("year", String.valueOf(year));
        String monthString = yearString.replace("month", String.valueOf(month));
        String dayString = monthString.replace("day", String.valueOf(day));
        cell.setCellValue(dayString);
    }

    /**
     * 针对UserModel导出方法
     * @param detailModelList
     * @param temp
     * @param export
     */
    public static void writeDetailModelExport(List<DetailModel> detailModelList, File temp, File export) {
        writeModelExport(DetailModel.class,1,2,new DetailModel().getStartRow(),1,detailModelList,temp,export);
    }

    /**
     * 针对UserModel导出方法
     * @param userModelList
     * @param temp
     * @param export
     */
    public static void writeUserModelExport(List<UserModel> userModelList, File temp, File export) {
        writeModelExport(UserModel.class,1,2,new UserModel().getStartRow(),1,userModelList,temp,export);
    }
    /**
     * 针对UserModel导出方法
     * @param graduateModelList
     * @param temp
     * @param export
     */
    public static void writeGraduateModelExport(List<GraduateModel> graduateModelList, File temp, File export) {
        writeModelExport(GraduateModel.class,1,2,new GraduateModel().getStartRow(),1,graduateModelList,temp,export);
    }
    /**
     * 针对UserModel导出方法
     * @param temp
     * @param export
     * @param isTime
     */
    public static void writeModelWorkBook(File temp, File export,Boolean isTime) {
        writeModelWorkBook(temp,export,0,1,9,isTime);
    }


    public static void writeUserExport(List<List<UserInfo>> usersList, File temp, File export) {
        try {
            InputStream inputStream = new FileInputStream(temp);
            OutputStream outputStream = new FileOutputStream(export);
            ExcelWriter writer = EasyExcelFactory.getWriterWithTemp(inputStream, outputStream, ExcelTypeEnum.XLSX,false);
            for (int i = 0; i < usersList.size(); i++ ) {
                List<UserModel> userModelList = new ArrayList<>();
                for (int index = 0; index < usersList.get(i).size(); index++ ) {
                    UserInfo userInfo = usersList.get(i).get(index);
                    UserModel userModel = ConvertUtils.userInfo2UserModel(userInfo);
                    userModel.setIndex(String.valueOf(index+1));
                    userModelList.add(userModel);
                }
                Sheet sheet = new Sheet(i+1, 2);
                sheet.setStartRow(new UserModel().getStartRow());
                writer.write(userModelList, sheet, initTable(1,UserModel.class));
            }
            writer.finish();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeModelCloneSheet(File temp, File export,List<Map<String,String>> replaceMapList) {
        writeModelCloneSheet(temp,export,0,0,0,replaceMapList);
    }
}
