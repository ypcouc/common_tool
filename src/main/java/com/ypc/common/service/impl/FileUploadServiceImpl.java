package com.ypc.common.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ypc.common.pojo.FileUploadInfo;
import com.ypc.common.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	/**允许上传的文件类型*/
	public static final String fileType = "pdf,png,bmp,jpg,xls,xlsx";
	/**允许上传的文件最大大小(10M,单位为byte)*/
	public static final int maxSize = 1024*1024*10;
	@Override
	public FileUploadInfo upload(MultipartFile file, String visitDir, String fileDisk,String businessCode) {
		FileUploadInfo uploadInfo = new FileUploadInfo();
		try {
			String fileName = file.getOriginalFilename();
			String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
					.substring(fileName.lastIndexOf(".") + 1).toLowerCase().trim();

			List<String> flieTypeList = Arrays.asList(fileType.split(","));
			if(StringUtils.isEmpty(extension) ||
					!flieTypeList.contains(extension)){
				uploadInfo.setState(0);
				uploadInfo.setMessage("上传文件格式不正确，只能是图片和PDF文件！");
				return uploadInfo;
			}
			if(file.getSize() > maxSize){
                uploadInfo.setState(0);
                uploadInfo.setMessage("上传文件太大，最大只能是"+maxSize/(1024*1024)+"M");
                return uploadInfo;
            }
			File fileDiskDir = new File(fileDisk);
			if (!fileDiskDir.isDirectory()) { //目录不存在
				fileDiskDir.mkdir(); //创建目录
			}
			//自定义文件名
			String custFileName = UUID.randomUUID().toString().replace("-", "")+"."+extension;
			String filePath = fileDisk + File.separator + businessCode + custFileName;
			visitDir = visitDir + File.separator + businessCode + custFileName;
			//判断文件是否已经存在
			File delFile = new File(filePath);
			if(delFile.exists()){
				FileUtils.forceDelete(new File(filePath));
				System.out.println("重复文件删除成功！");
			}
			File importExcel = new File(filePath);
			try {
				InputStream is = file.getInputStream();
				FileOutputStream os = new FileOutputStream(importExcel);
				byte[] by = new byte[1024 * 10];
				while (is.read(by) != -1) {
					os.write(by);
				}
				is.close();
				os.close();
			} catch (Exception e) {
				uploadInfo.setState(0);
				uploadInfo.setMessage("上传失败！");
				e.printStackTrace();
			}

			if(extension.equals("pdf")){
                String shell = "convert "+filePath+" -append -flatten "
                        +filePath.substring(0,filePath.lastIndexOf("."))+".png";
                class DoShell implements Runnable{
                    private String shell;
                    DoShell(String shellstr){
                        shell = shellstr;
                    }
                    @Override
                    public void run() {
                        try {
                            Runtime.getRuntime().exec(shell);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                DoShell doShell = new DoShell(shell);
                new Thread(doShell).start();
            }

			uploadInfo.setState(1);
			uploadInfo.setMessage("上传成功！");
			uploadInfo.setFileName(fileName);
			uploadInfo.setFileDir(visitDir.replaceAll("\\\\","/"));
			uploadInfo.setFilePath(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadInfo;
	}

}
