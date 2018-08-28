package com.zhixin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("fastdfs")
public class FastdfsProperties {

    @Value("60")
    private String connectTimeout;
    @Value("80")
    private String networkTimeout;
    @Value("false")
    private String httpAntiStealToken;
    @Value("http://")
    private String protocol;
    @Value("10.252.1.177:22122,10.252.1.178:22122")
    private String trackerServers;
    @Value("8080")
    private String trackerHttpPort;
    @Value("10.252.1.177:8888")
    private String nginxAddress;
    @Value("100")
    private String maxTotal;
    @Value("30")
    private String maxIdle;


    public String getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(String connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getNetworkTimeout() {
        return networkTimeout;
    }

    public void setNetworkTimeout(String networkTimeout) {
        this.networkTimeout = networkTimeout;
    }

    public String getHttpAntiStealToken() {
        return httpAntiStealToken;
    }

    public void setHttpAntiStealToken(String httpAntiStealToken) {
        this.httpAntiStealToken = httpAntiStealToken;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getTrackerServers() {
        return trackerServers;
    }

    public void setTrackerServers(String trackerServers) {
        this.trackerServers = trackerServers;
    }

    public String getTrackerHttpPort() {
        return trackerHttpPort;
    }

    public void setTrackerHttpPort(String trackerHttpPort) {
        this.trackerHttpPort = trackerHttpPort;
    }

    public String getNginxAddress() {
        return nginxAddress;
    }

    public void setNginxAddress(String nginxAddress) {
        this.nginxAddress = nginxAddress;
    }


    public String getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(String maxTotal) {
        this.maxTotal = maxTotal;
    }

    public String getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }
}
