package com.swsm.proxynet.client.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenxin
 * @date 2023/08/30 10:20
 */
@Configuration
@Data
public class ProxyConfig {

    @Value("${proxynet.client.id}")
    private Integer clientId;

    @Value("${proxynet.client.serverIp}")
    private String serverIp;
    @Value("${proxynet.client.serverPort}")
    private Integer serverPort;
    
    
}
