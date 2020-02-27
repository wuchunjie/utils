package com.wcj.utils.configuration.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/6/27 0027
 * @time: 下午 14:56
 * @Description: jides 配置
 */
@Data
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    private String host;
    private int port;
}
