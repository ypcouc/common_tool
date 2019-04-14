package com.ypc.common.utils;

import net.sf.jxls.transformer.XLSTransformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by dodo on 2017/9/12.
 */
public class ExportUtils {

    public static String export(String templateFileName, Map beans, OutputStream out) {
        String info = "success!!!";
        File temp = new File("");
        //模板文件
        try {
            String template = temp.getAbsolutePath() + "/src/main/webapp/template/"+ templateFileName;
            String result = temp.getAbsolutePath() + "/src/main/webapp/result/result1.xls";
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(template, beans, result);

            FileInputStream in = new FileInputStream(result);
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        } catch (Exception e) {
           e.printStackTrace();
           info = "fail!!!";
        }
        return info;
    }

}
