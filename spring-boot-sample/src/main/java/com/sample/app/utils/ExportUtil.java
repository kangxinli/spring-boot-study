package com.sample.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 浏览器导出文件
 */
public class ExportUtil {
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    public ExportUtil(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }
    public void export(String path, String name) throws Exception {
        try{
            String templateFileName = request.getSession().getServletContext().getRealPath(path);
            //将文件响应到客户端
            File sourceFile = new File(templateFileName);
            String fileName = null;
            String userAgent = request.getHeader("User-Agent");
            if(userAgent.contains("MSIE")){//IE浏览器
                fileName = URLEncoder.encode(name,"UTF-8");
            }else if(userAgent.contains("Mozilla")){//google,火狐浏览器
                fileName = new String(name.getBytes(), "ISO8859-1");
            }else{
                fileName = URLEncoder.encode(name,"UTF-8");//其他浏览器
            }
            response.setContentType("multipart/form-data");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);// 将文件名转码
            byte[] b = new byte[1024];
            int length;
            InputStream inputStream = new FileInputStream(sourceFile);
            OutputStream outputStream = response.getOutputStream();
            while ((length = inputStream.read(b)) > 0) {
            	outputStream.write(b, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}