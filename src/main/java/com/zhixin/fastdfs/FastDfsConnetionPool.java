 package com.zhixin.fastdfs;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.Closeable;
import java.io.IOException;
/**
 * @author yignkui.shen
 * @date 2018年3月22日下午6:37:31
 * @version 1.0
 * 
 * 连接池工厂
 * */
public class FastDfsConnetionPool extends BasePooledObjectFactory<StorageClient1> {

    private FastDFSTemplate template;

    public FastDfsConnetionPool(FastDFSTemplate templateFactory) {
        this.template = templateFactory;
    }

    @Override
    public StorageClient1 create() throws Exception {
        TrackerClient trackerClient = new TrackerClient(template.getG_tracker_group());
        TrackerServer trackerServer = trackerClient.getConnection();
        return new StorageClient1(trackerServer, null);
    }

    @Override
    public PooledObject<StorageClient1> wrap(StorageClient1 storageClient) {
        // TODO Auto-generated method stub
        return new DefaultPooledObject<>(storageClient);
    }
    
    public PooledObject<StorageClient1> makeObject() throws Exception {
        return wrap(create());
    }
    public void destroyObject(StorageClient1 obj) throws Exception {
        close(obj.getTrackerServer());
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
    
}
