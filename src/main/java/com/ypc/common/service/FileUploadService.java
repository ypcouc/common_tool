package com.ypc.common.service;

import org.springframework.web.multipart.MultipartFile;

import com.ypc.common.pojo.FileUploadInfo;

public interface FileUploadService {

	/**
	 * 文件上传
	 * @param file
	 * @param visitDir 访问路径
	 * @param fileDisk 上传路径
	 * @param businessCode 业务编号
	 * @return
	 */
	FileUploadInfo upload(MultipartFile file, String visitDir, String fileDisk, String businessCode);
}
