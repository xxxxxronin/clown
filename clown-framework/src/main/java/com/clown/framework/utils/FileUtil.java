package com.clown.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传下载
 * @author allen.zhang
 *
 */
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	
	/**
	 * 单文件上传
	 * @param file 文件
	 * @param savePath 保存路径
	 * @param separator 路径分隔符
	 * @return
	 * @throws IOException
	 */
	public static String upLoadFile(MultipartFile file, String savePath, String separator) throws IOException{
		
		File dir = new File(savePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String uuid = UUID.randomUUID().toString();
		String path = dir.getPath()+separator+uuid;
		try {
			InputStream is = file.getInputStream();
			saveFile(is, new FileOutputStream(path));
		}catch (FileNotFoundException e) {
			logger.error("上传的文件: " + file.getName() + " 不存在！！");  
            e.printStackTrace();
		} 
		
	    return uuid;
	}
	
	/**
	 * 多文件上传
	 * @param files 文件
	 * @param savePath 保存路径
	 * @param separator 路径分隔符
	 * @return
	 * @throws IOException
	 */
	public static String[] upLoadFiles(MultipartFile[] files, String savePath, String separator)throws IOException,FileNotFoundException{	
		File dir = new File(savePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String[] uuids = new String[files.length];
		int i = 0;
		
		for (MultipartFile file : files) {
			String uuid = UUID.randomUUID().toString();
			String path = dir.getPath()+separator+uuid;

			InputStream is = file.getInputStream();
			saveFile(is, new FileOutputStream(path));
	
			uuids[i] = uuid;
			i++;
		}
		
	    return uuids;
	}
	
	/**
	 * save file
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	private static void saveFile(InputStream is, OutputStream os) throws IOException{
		byte[] bytes = new byte[1024];
		int len = -1;
		try {
			while ((len=is.read(bytes)) != -1) {
				os.write(bytes, 0, len);
			}
			os.flush();
		} finally{
			os.close();
			is.close();
		}
	}
	
	
	/**
	 * 文件下载
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static byte[] downLoadFile(String path) throws IOException,FileNotFoundException{
		   File file = new File(path);
		   if (file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				return StreamUtils.copyToByteArray(fis);
			} else {
				throw new FileNotFoundException();
			}
	   }
}
