package com.ypc.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author: ypc
 * @Date: 2018-06-30 11:27
 * @Description:
 * @Modified By:
 */
@Configuration
public class ElasticSearchConfig {
    /**
     * 防止netty的bug
     * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
     */
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

}
