package com.ypc.common.utils.export;

import com.ypc.common.utils.AttachmentUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.Random;

public class ExcelExportOrg {

    /**
     * 导出为通用的列表模板
     *
     * @param userAgent      客户端浏览器信息
     * @param context 打印数据的信息
     * @param fileName       下载文件的名称，不需要包含文件后缀
     * @return
     * @throws IOException
     */
    public static ResponseEntity<byte[]> simpleExport(String userAgent,
                                                      Context context, String fileName) throws IOException {
        byte[] fileData = getSimpleExportFile(context, fileName);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("applicatoin/octet-stream"));
        //下载名称
        String outputFileName = fileName;
        httpHeaders.set("Content-disposition", "attachment; filename=" + AttachmentUtils.getDownloadFileName(outputFileName, userAgent));
        return new ResponseEntity<byte[]>(fileData, httpHeaders, HttpStatus.OK);
    }

    /**
     * 导出为通用的列表模板
     *
     * @param context 打印数据的信息
     * @param fileName       下载文件的名称，不需要包含文件后缀
     * @return
     * @throws IOException
     */
    public static byte[] getSimpleExportFile(Context context, String fileName) {
        byte[] fileData = null;
        InputStream is = null;
        OutputStream os = null;

        String templateFilePath = "";
        try {
            templateFilePath = AttachmentUtils.getPrintModelDir() + fileName;
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //获取文件后缀
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
                .substring(fileName.lastIndexOf(".") + 1);
        String fileNameStr = fileName.substring(0, fileName.lastIndexOf("."));
        //打印中临时文件生成的名称,添加随机数后缀
        Random rNumber = new Random();//生成随机数
        String tempFilePath = "";
        try {
            tempFilePath = AttachmentUtils.getPrintTempDir() + fileNameStr + rNumber.nextInt() + "." + extension;
            is = new FileInputStream(new File(templateFilePath));
            os = new FileOutputStream(tempFilePath);
            JxlsHelper.getInstance().setUseFastFormulaProcessor(false).processTemplate(is, os, context);

            File tempFile = new File(tempFilePath);
            fileData = FileCopyUtils.copyToByteArray(new FileInputStream(tempFile));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileData;
    }
}
