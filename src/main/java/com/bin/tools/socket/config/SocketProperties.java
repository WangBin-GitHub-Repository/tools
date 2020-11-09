package com.bin.tools.socket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Socket 配置
 * @author bin.wang
 * @version 1.0 2020/9/24
 */
@Configuration
@ConfigurationProperties(prefix = "socket")
public class SocketProperties {
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
