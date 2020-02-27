package com.wcj.utils.configuration.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/6/27 0027
 * @time: 下午 14:56
 * @Description:
 */
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
