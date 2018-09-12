package com.ypc.excel;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;

public class ExcelExportTools {
    public static String pintModelDir = "";//导出模板地址
    public static String printTempDir = "";//临时地址

    /**
     * 检查文件是否存在
     *
     * @param filePath
     * @return
     */
    private static boolean checkFile(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static ResponseEntity<byte[]> simpleExport(String userAgent,
                                                      Map<String, Object> data, String fileName) throws IOException {

        //获取文件后缀
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
                .substring(fileName.lastIndexOf(".") + 1);
        String fileNameStr = fileName.substring(0, fileName.lastIndexOf("."));
        byte[] fileData = null;

        //模板路径
        String templateFilePath = pintModelDir + fileName;
        if (!checkFile(templateFilePath)) {
            throw new RuntimeException("没找到模板！：" + templateFilePath);
        } else {

            //打印中临时文件生成的名称,添加随机数后缀
            Random rNumber = new Random();//生成随机数
            String tempFilePath = printTempDir + fileNameStr + rNumber.nextInt() + "." + extension;

            XLSTransformer transformer = new XLSTransformer();
            try {
                transformer.transformXLS(templateFilePath, data, tempFilePath);

                File tempFile = new File(tempFilePath);
                fileData = FileCopyUtils.copyToByteArray(new FileInputStream(tempFile));

                //删除临时文件
                deleteTempFile(tempFilePath);
            } catch (ParsePropertyException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //httpHeaders.setContentType(MediaType.parseMediaType("applicatoin/octet-stream"));
        //下载名称
        String outputFileName = fileName;
        httpHeaders.setContentDispositionFormData("attachment", getDownloadFileName(outputFileName, userAgent));//告知浏览器以下载方式打开
        //httpHeaders.set("Content-disposition", "attachment; filename=" + AttachmentUtils.getDownloadFileName(outputFileName, userAgent));
        return new ResponseEntity<byte[]>(fileData, httpHeaders, HttpStatus.OK);
    }

    /**
     * 删除临时文件
     *
     * @param tempFilePath 销毁的文件
     * @return
     */
    private static boolean deleteTempFile(String tempFilePath) {
        if (StringUtils.hasText(tempFilePath)) {
            File tempFile = new File(tempFilePath);
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
        return true;
    }

    /**
     * 根据浏览器生成下载文件名
     *
     * @param filename  下载时的文件名
     * @param userAgent 浏览器类型信息
     * @throws UnsupportedEncodingException
     */
    public static String getDownloadFileName(String filename, String userAgent) throws UnsupportedEncodingException {
        String filName = "";
        if (StringUtils.isEmpty(userAgent)) {
            throw new RuntimeException("未指定浏览器类型，下载文件名称可能会乱码");
        }
        userAgent = userAgent.toLowerCase();
        if (userAgent.indexOf("msie") != -1) {//IE浏览器
            filName = URLEncoder.encode(filename, "UTF8");
        } else if (userAgent.indexOf("opera") != -1) {// Opera浏览器只能采用filename*
            filName = "filename*=UTF-8''" + filename;
        } else if (userAgent.indexOf("safari") != -1) {  // Safari浏览器，只能采用ISO编码的中文输出
            filName = new String(filename.getBytes("UTF-8"), "ISO8859-1");
        } else if (userAgent.indexOf("applewebkit") != -1) {// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
            filName = new String(filename.getBytes("UTF-8"), "ISO8859-1");
        } else if (userAgent.indexOf("mozilla") != -1) {// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
            filName = new String(filename.getBytes("UTF-8"), "ISO8859-1");
        }
        return filName;
    }
}
