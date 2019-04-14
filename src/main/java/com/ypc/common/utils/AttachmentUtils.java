package com.ypc.common.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 上传基本路径 通过system.properties设置
 * 系统中附件存储的路径规则为
 * 业务的相关模板路径：业务编码/MODEL/业务附件编码id/file_databaseid.fileSuffix(附件模板数据id.文件后缀)
 * 用户上传附件路径：   业务编码/BUSINESS/businessId(业务数据ID,业务附件对象中存储的， 数据主键值 )/file_databaseid.fileSuffix(附件数据id.文件后缀)
 * <p>
 * 打印模板路径可以通过system.properties设置
 * 模板路径:MODEL
 * 临时文件生成目录:TEMP
 *
 * @author Administrator
 */
public class AttachmentUtils {

    /**
     * 上传模板、用户附件路径 在 system.properties 下的参数名
     */
    public static final String UPLOAD_BASE_DIR = "uploadBaseDir";

    /**
     * 上传模板、用户附件路径 在 system.properties 下的参数名
     */
    public static final String UPLOAD_BASE_DIR1 = "uploadBaseDir1";

    /**
     * 上传模板、用户附件路径 在 system.properties 下的参数名
     */

    public static final String UPLOAD_BASE_ZIP = "uploadBaseZip";

    /**
     * 打印模板路径 在 system.properties 下的参数名
     */
    public static final String PRING_BASE_DIR = "printBaseDir";

    /**
     * 上传文件 使用AttachmentService中的uploadAttachment方法
     * @param request
     * @param uploadPath 结构类似 dir1/dir2
     * @param manualSetFileName 是否存储时人工设置文件名称
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    /*
    public static String uploadFile(HttpServletRequest request,String uploadPath,String manualSetFileName){
    	String result=CommonConstants.FAILED;
		try {
			PropertiesFactoryBean pfb=SpringContextsUtil.getBean(PropertiesFactoryBean.class);
			Properties properties=pfb.getObject();
			String uploadBaseDir=properties.getProperty(UPLOAD_BASE_DIR);
	    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    	multipartRequest.getFileMap();
	    	Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
	    	MultipartFile multipartFile =null;
	    	for(Map.Entry<String,MultipartFile > fileSet:fileMap.entrySet()){
//	    		String filekey = set.getKey();//Filedata
	    		multipartFile=fileSet.getValue();
	    		multipartFile.getContentType();
	    		String fileName=StringUtils.isNotEmpty(manualSetFileName)?manualSetFileName:multipartFile.getOriginalFilename();
	    		multipartFile.transferTo(new File(uploadBaseDir+uploadPath+fileName));
	    	}
		} catch (Exception e) {
			RuntimeException re=new RuntimeException("上传附件失败", e);
			throw re;
		}
		return result;
    }
    */
    /**
     * 用于上传控件的附件获取
     * @param request
     * @return
     * @throws IOException
     */
    /*public static UploadFile getUploadFileToByte(HttpServletRequest request) throws IOException  {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    	multipartRequest.getFileMap();
    	Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    	UploadFile uploadFile=new UploadFile();
    	for(Map.Entry<String,MultipartFile > fileSet:fileMap.entrySet()){
    		MultipartFile multipartFile=fileSet.getValue();
    		uploadFile.setFileData(multipartFile.getBytes());
    		uploadFile.setFileName(multipartFile.getOriginalFilename());
    	}
    	return uploadFile;
    }*/

    /**
     * 读取上传的根目录
     *
     * @return
     * @throws IOException
     */
    public static String getUploadBaseDir() throws IOException {
        return PropertiesUtils.getPropertyValue(AttachmentUtils.UPLOAD_BASE_DIR);
    }

    /**
     * 读取打印根目录
     *
     * @return
     * @throws IOException
     */
    public static String getPrintBaseDir() throws IOException {
        return PropertiesUtils.getPropertyValue(AttachmentUtils.PRING_BASE_DIR);
    }

    /**
     * 读取打印模板目录
     *
     * @return
     * @throws IOException
     */
    public static String getPrintModelDir() throws IOException {
        return PropertiesUtils.getPropertyValue(AttachmentUtils.PRING_BASE_DIR) + "template" + File.separator;
    }

    /**
     * 返回打印中临时文件生成的目录
     *
     * @return
     * @throws IOException
     */
    public static String getPrintTempDir() throws IOException {
        return PropertiesUtils.getPropertyValue(AttachmentUtils.PRING_BASE_DIR) + "temp" + File.separator;
    }

    /**
     * 删除文件
     *
     * @param path
     * @throws IOException
     */
    public static void deleteFile(String path) throws IOException {
        String uploadBaseDir = getUploadBaseDir();
        FileUtils.forceDelete(new File(uploadBaseDir + path));
    }

    public static ResponseEntity<byte[]> getEmptyFile() {
        return new ResponseEntity<byte[]>(null, null, HttpStatus.NO_CONTENT);
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
