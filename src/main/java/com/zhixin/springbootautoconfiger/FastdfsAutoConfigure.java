package com.zhixin.springbootautoconfiger;

import com.zhixin.FastdfsProperties;
import com.zhixin.fastdfs.FastDFSClientUtil2;
import com.zhixin.fastdfs.FastDFSTemplate;
import com.zhixin.fastdfs.PoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(FastDFSTemplate.class)
@EnableConfigurationProperties(FastdfsProperties.class)
public class FastdfsAutoConfigure {
    @Autowired
    private FastdfsProperties servcieProperties;

    @Autowired
    private FastDFSTemplate fastDFSFactory;
    @Bean
    @ConditionalOnMissingBean
    FastDFSTemplate autoConfigerService(){
        /*//初始化配置文件赋值
        FastDFSTemplate temaplte = new FastDFSTemplate();
        temaplte.setG_anti_steal_token(Boolean.valueOf(servcieProperties.getHttpAntiStealToken()));
        temaplte.setG_charset("utf-8");
        temaplte.setG_connect_timeout(Integer.parseInt(servcieProperties.getConnectTimeout()));
        temaplte.setG_network_timeout(Integer.parseInt(servcieProperties.getNetworkTimeout()));
        temaplte.setTracker_servers(servcieProperties.getTrackerServers());
        temaplte.setNginx_address(servcieProperties.getNginxAddress());
        PoolConfig config = new PoolConfig();
        config.setMaxIdle(Integer.valueOf(servcieProperties.getMaxIdle()));
        config.setMaxTotal(Integer.valueOf(servcieProperties.getMaxTotal()));
        temaplte.setPoolConfig(config);

        return temaplte;*/

        //初始化配置文件赋值
        FastDFSTemplate temaplte = new FastDFSTemplate();
        temaplte.setG_anti_steal_token(false);
        temaplte.setG_charset("utf-8");
        temaplte.setG_connect_timeout(60);
        temaplte.setG_network_timeout(80);
        temaplte.setTracker_servers("10.252.1.177:22122,10.252.1.178:22122");
        temaplte.setNginx_address("10.252.1.177:8888");
        PoolConfig config = new PoolConfig();
        config.setMaxIdle(30);
        config.setMaxTotal(100);
        temaplte.setPoolConfig(config);

        return temaplte;
    }
    @Bean
    @ConditionalOnMissingBean
    public FastDFSClientUtil2 fastDFSTemplate() {
        return new FastDFSClientUtil2(autoConfigerService());
    }
}
