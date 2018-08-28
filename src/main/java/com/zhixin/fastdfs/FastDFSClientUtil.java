package com.zhixin.fastdfs;

import com.zhixin.fastdfs.commons.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import sun.misc.IOUtils;

import java.io.*;
import java.net.URLEncoder;


public class FastDFSClientUtil {
	
	private static StorageClient1 storageClient1 = null;
	
	static {
		try {
			System.out.println(" ==== 初始化参数 ==== ");
//			ClientGlobal.init("G:\\fdfs_client.conf");
			TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
			TrackerServer trackerServer = trackerClient.getConnection();
			if(trackerServer == null)
				System.out.println("getConnection return null.");
			StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
			if(storageServer == null)
				System.out.println("getStorageStorage return null.");
			storageClient1 = new StorageClient1(trackerServer, null);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static String uploadFile(File file, String fileName){
		FileInputStream fileInputStream = null;
		try {
			NameValuePair[] meta_list = null;
			fileInputStream = new FileInputStream(file);
			byte[] file_buff = null;
			if(fileInputStream != null){
				int len =fileInputStream.available();
				file_buff = new byte[len];
				fileInputStream.read(file_buff);
			}
			String fileid = storageClient1.upload_file1(file_buff, getFileExt(fileName), meta_list);
			System.out.println("返回ID = " + fileid);
			return fileid;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static int deleteFile(String fileId){
		try {
			int result = storageClient1.delete_file1(fileId);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	
	public static String modifyFile(String groupName, String oldFileId, File file, String filePath){
		String fileId = null;
		try {
			//先上传
			fileId = uploadFile(file, filePath);
			if(fileId == null)
				return null;
			//再删除
			int delResult = deleteFile(oldFileId);
			if(delResult == 0)
				return null;
			return fileId;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static InputStream downloadFile(String fileId) {
		try {
			byte[] bytes = storageClient1.download_file1(fileId);
			InputStream inputStream = new ByteArrayInputStream(bytes);
			return inputStream;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	

	private static String getFileExt(String fileName){
		if(StringUtils.isBlank(fileName) || !fileName.contains("."))
			return "";
		else 
			return fileName.substring(fileName.lastIndexOf(".") + 1); //不带最后的一点
	}
}
