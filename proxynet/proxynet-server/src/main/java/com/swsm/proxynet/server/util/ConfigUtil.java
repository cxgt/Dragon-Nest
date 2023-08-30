package com.swsm.proxynet.server.util;

import com.swsm.proxynet.server.config.ProxyConfig;
import java.util.List;
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
    
    public static List<ProxyConfig.ProxyInfo> getProxyInfos() {
        return myProxyConfig.getProxyInfos();
    }
    
    public static ProxyConfig.ProxyInfo getProxyInfo(int serverPort) {
        for (ProxyConfig.ProxyInfo proxyInfo : getProxyInfos()) {
            if (proxyInfo.getServerPort() == serverPort) {
                return proxyInfo;
            }
        }
        return null;
    }
    
    
}
