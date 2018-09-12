package com.ypc.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageOperateUtils {
	
	/**
	 * 图片比例压缩
	 * @param fromPicUrl 原图片路径
	 * @param toPic 压缩后图片目录
	 * @param format 压缩格式
	 * @param scale 压缩比例
	 * @return
	 * @throws IOException
	 */
	public static String imageScaleCompress(String fromPicUrl,String toPic,String format,float scale) throws IOException{
		if(StringUtils.isEmpty(fromPicUrl) || StringUtils.isEmpty(toPic)
				|| StringUtils.isEmpty(format) || scale == 0){
			return null;
		}
		Thumbnails.of(fromPicUrl)  
	    .scale(scale)  
	    .outputFormat(format)  
	    .toFile(toPic); 
		return toPic+"."+format;
	}
	
	/**
	 * 图片大小压缩   图片尺寸不变，压缩图片文件大小
	 * @param fromPicUrl 原图片路径
	 * @param toPic 压缩后图片目录
	 * @param format 压缩格式
	 * @param quality 压缩质量比例
	 * @return
	 * @throws IOException
	 */
	public static String imageQualityCompress(String fromPicUrl,String toPic,String format,float quality) throws IOException{
		if(StringUtils.isEmpty(fromPicUrl) || StringUtils.isEmpty(toPic)
				|| StringUtils.isEmpty(format) || quality == 0){
			return null;
		}
		Thumbnails.of(fromPicUrl)  
	    .scale(1f)
	    .outputQuality(quality)
	    .outputFormat(format)  
	    .toFile(toPic); 
		return toPic+"."+format;
	}
	
	
	/**
	 * 指定图片大小压缩，不保持原图比例
	 * @param fromPicUrl 原图片路径
	 * @param toPic 压缩后图片目录
	 * @param format 压缩格式
	 * @param width 压缩宽
	 * @param height 压缩高
	 * @return
	 * @throws IOException
	 */
	public static String imageSizeCompress(String fromPicUrl,String toPic,
				String format,int width,int height) throws IOException{
		if(StringUtils.isEmpty(fromPicUrl) || StringUtils.isEmpty(toPic)
				|| StringUtils.isEmpty(format) || width == 0 || height == 0){
			return null;
		}
		Thumbnails.of(fromPicUrl)
		.forceSize(width, height)
		.outputFormat(format)
		.toFile(toPic); 
		return toPic+"."+format;
	}
	
	/**
	 * 指定图片大小压缩，保持原图比例
	 * @param fromPicUrl 原图片路径
	 * @param toPic 压缩后图片目录
	 * @param format 压缩格式
	 * @param width 压缩宽
	 * @param height 压缩高
	 * @param position 位置  例如： Positions.CENTER
	 * @return
	 * @throws IOException
	 */
	public static String imageHoldSizeCompress(String fromPicUrl,String toPic,
				String format,int width,int height,Positions position) throws IOException{
		if(StringUtils.isEmpty(fromPicUrl) || StringUtils.isEmpty(toPic)
				|| StringUtils.isEmpty(format) || width == 0 || height == 0
				|| position == null){
			return null;
		}
		BufferedImage image = ImageIO.read(new File(fromPicUrl));  
		Builder<BufferedImage> builder = null;  
		  
		int imageWidth = image.getWidth();  
		int imageHeitht = image.getHeight();  
		if ((float)height / width != (float)imageWidth / imageHeitht) {  
		    if (imageWidth > imageHeitht) {  
		        image = Thumbnails.of(fromPicUrl).height(height).asBufferedImage();  
		    } else {  
		        image = Thumbnails.of(fromPicUrl).width(width).asBufferedImage();  
		    }  
		    builder = Thumbnails.of(image).sourceRegion(position, width, height).size(width, height);  
		} else {  
		    builder = Thumbnails.of(image).size(width, height);  
		}  
		builder.outputFormat(format).toFile(toPic);  
		return toPic+"."+format;
	}
	
	/**
	 * 图片裁剪
	 * @param fromPicUrl 原图片路径
	 * @param toPic 压缩后图片目录
	 * @param format 压缩格式
	 * @param width 压缩宽
	 * @param height 压缩高
	 * @param position 位置  例如： Positions.CENTER
	 * @return
	 * @throws IOException
	 */
	public static String imageCut(String fromPicUrl,String toPic,
				String format,int width,int height,Positions position) throws IOException{
		if(StringUtils.isEmpty(fromPicUrl) || StringUtils.isEmpty(toPic)
				|| StringUtils.isEmpty(format) || width == 0 || height == 0
				|| position == null){
			return null;
		}
		Thumbnails.of(fromPicUrl)
		.sourceRegion(position,width,height)  
		.size(width,height)
		.outputFormat(format)
		.toFile(toPic);
		return toPic+"."+format;
	}
	
	

}
