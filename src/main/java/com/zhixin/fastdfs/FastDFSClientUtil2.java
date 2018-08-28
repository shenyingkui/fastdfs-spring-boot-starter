package com.zhixin.fastdfs;

import com.zhixin.fastdfs.commons.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import sun.misc.IOUtils;

import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * 通过连接池获取storageClient
 * @author shenyingkui
 * @version 1.0
 * @date 2018年3月22日下午6:15:38
 * */
public class FastDFSClientUtil2 {

	private ConnectionPoolFactory connPoolFactory;
    private FastDFSTemplate factory;

    public FastDFSClientUtil2(FastDFSTemplate factory) {
        this.connPoolFactory = new ConnectionPoolFactory(factory);
        this.factory = factory;
    }

    /**
	 * 数据流上传
	 * */
	public  String uploadFile(byte[] bytes, String fileName){

		StorageClient1 storageClient1 = null;
		try {
			NameValuePair[] meta_list = null;

			storageClient1 = getClient();
			String fileid = storageClient1.upload_file1(bytes, getFileExt(fileName), meta_list);
			return fileid;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			connPoolFactory.releaseConnection(storageClient1);
		}
		return null;
	}
	public  String uploadFile(File file, String fileName){
		FileInputStream fileInputStream = null;
		StorageClient1 storageClient1 = null;
		try {
			NameValuePair[] meta_list = null;
			fileInputStream = new FileInputStream(file);
			byte[] file_buff = null;
			if(fileInputStream != null){
				int len =fileInputStream.available();
				file_buff = new byte[len];
				fileInputStream.read(file_buff);
			}
			storageClient1 = getClient();
			String fileid = storageClient1.upload_file1(file_buff, getFileExt(fileName), meta_list);
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
			connPoolFactory.releaseConnection(storageClient1);
		}
		return null;
	}
	
	public  int deleteFile(String fileId){
		StorageClient1 storageClient1 = null;
		try {
			storageClient1 = getClient();
			int result = storageClient1.delete_file1(fileId);
			return result;
		} catch (Exception e) {
		}finally {
			connPoolFactory.releaseConnection(storageClient1);
		}
		return 0;
	}
	
	public  String modifyFile(String groupName, String oldFileId, File file, String filePath){
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
		}
		return null;
	}
	
	public  InputStream downloadFile(String fileId) {
		StorageClient1 storageClient1 = null;
		try {
			storageClient1 = getClient();
			byte[] bytes = storageClient1.download_file1(fileId);
			InputStream inputStream = new ByteArrayInputStream(bytes);
			return inputStream;
		} catch (Exception e) {
		}finally {
				connPoolFactory.releaseConnection(storageClient1);
		}
		return null;
	}



	
	
	private  String getFileExt(String fileName){
		if(StringUtils.isBlank(fileName) || !fileName.contains(".")) {
			return "";
		}else {
			return fileName.substring(fileName.lastIndexOf(".") + 1); //不带最后的一点
		}
	}
	
	 protected StorageClient1 getClient() {
	        StorageClient1 client = null;
	        int i =0 ;
	        while (client == null) {
	        	i++;
	            try {
	                client = connPoolFactory.getClient();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            if(i > 5){
	            	break;
				}
	        }
	        return client;
	    }

	    protected void releaseClient(StorageClient1 client) {
			connPoolFactory.releaseConnection(client);
	    }
}
