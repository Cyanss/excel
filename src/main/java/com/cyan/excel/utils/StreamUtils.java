package com.cyan.excel.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 流处理工具类
 * @auther Cyan
 * @create 2019/6/10
 */
public class StreamUtils {

    /**
     * request请求的输入流转换为String
     * @param inputStream
     * @return
     */
    public static String iStream2String(InputStream inputStream)  {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        try {
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            streamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStrBuilder.toString();
    }

    /**
     * 将字符串写进输出流里
     * @param response
     * @param jsonString
     */
    public static void string2Response(HttpServletResponse response, String jsonString)  {
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            OutputStream outputStream = response.getOutputStream();
            string2OString(outputStream,jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将字符串写进输出流里
     * @param jsonString
     */
    public static void string2OString(OutputStream outputStream, String jsonString)  {
        try {
            outputStream.write(jsonString.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param response
     * @param bytes
     */
    public static void bytes2Response(HttpServletResponse response, byte[] bytes) {
        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * request请求的输入流转换为byte字节数组
     * @param inputStream
     * @return
     */
    public static byte[] iStream2Bytes(InputStream inputStream)  {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        byte[] bytes = null;
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            bytes = byteArrayOutputStream.toByteArray();
            inputStream.close();
            byteArrayOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * byte[]字节数组写入文件
     * @param bytes
     * @param file
     */
    public static void bytes2File(byte[] bytes, File file) {

        try {
            OutputStream outputStream = new FileOutputStream(file);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void file2Response(String fileName, HttpServletResponse response, File export) {
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode(fileName, "UTF-8")+".xlsx");
            response.setContentType("application/octet-stream");
            FileInputStream fileInputStream = new FileInputStream(export);
            byte[] content = new byte[fileInputStream.available()];
            int read = fileInputStream.read(content);
            fileInputStream.close();
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.write(content);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
