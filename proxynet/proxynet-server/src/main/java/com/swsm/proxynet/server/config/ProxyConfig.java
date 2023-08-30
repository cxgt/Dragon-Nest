package com.swsm.proxynet.server.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenxin
 * @date 2023/08/30 10:20
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "proxynet.server")
public class ProxyConfig {

    private Integer clientPort;
    
    private List<ProxyInfo> proxyInfos;


    @Data
    public static class ProxyInfo {
        private Integer clientId;
        private String name;
        private Integer serverPort;
        private String targetIp;
        private Integer targetPort;
    }
    

}
