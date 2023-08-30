package com.swsm.proxynet.client.config;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenxin
 * @date 2023/08/30 10:20
 */
@Component
@Slf4j
public class ConfigUtil {
    
    @Autowired
    private ProxyConfig proxyConfig;
    
    private static ProxyConfig myProxyConfig;
    
    @PostConstruct
    public void init() {
        myProxyConfig = proxyConfig;
    }
    
    public static String getServerIp() {
        return myProxyConfig.getServerIp();
    }
    
    public static int getServerPort() {
        return myProxyConfig.getServerPort();
    }
    
    
}
