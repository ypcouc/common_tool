package com.ypc.common.utils.export;

import com.ypc.common.utils.AttachmentUtils;
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
import java.util.Map;
import java.util.Random;

public class ExcelExportTools {

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
        String templateFilePath = AttachmentUtils.getPrintModelDir() + fileName;
        if (!checkFile(templateFilePath)) {
            throw new RuntimeException("没找到模板！：" + templateFilePath);
        } else {

            //打印中临时文件生成的名称,添加随机数后缀
            Random rNumber = new Random();//生成随机数
            String tempFilePath = AttachmentUtils.getPrintTempDir() + fileNameStr + rNumber.nextInt() + "." + extension;

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
        httpHeaders.setContentDispositionFormData("attachment", AttachmentUtils.getDownloadFileName(outputFileName, userAgent));//告知浏览器以下载方式打开
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
}
