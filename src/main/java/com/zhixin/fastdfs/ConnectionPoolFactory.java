package com.zhixin.fastdfs;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * FastDFS 连接池工厂
 * @author shenyingkui
 */
public class ConnectionPoolFactory {

    private GenericObjectPool<StorageClient1> pool;


    public ConnectionPoolFactory(FastDFSTemplate fastDFSTemplateFactory) {
        pool = new GenericObjectPool<>(new FastDfsConnetionPool(fastDFSTemplateFactory));
    }

    public StorageClient1 getClient() throws Exception {
        return pool.borrowObject();
    }

    public void releaseConnection(StorageClient1 client) {
        try {
            pool.returnObject(client);
        } catch (Exception ignored) {
        }
    }


    private void toConfig(PoolConfig poolConfig) {
        pool.setMaxTotal(poolConfig.maxTotal);
        pool.setMaxIdle(poolConfig.maxIdle);
        pool.setMinIdle(poolConfig.minIdle);
        pool.setTestOnBorrow(poolConfig.testOnBorrow);
        pool.setMaxWaitMillis(poolConfig.maxWait);
    }

}