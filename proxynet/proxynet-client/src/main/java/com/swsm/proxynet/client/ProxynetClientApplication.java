package com.swsm.proxynet.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author chenxin
 * @date 2023/08/30 10:20
 */
@SpringBootApplication
@Slf4j
@ComponentScan(value = {"com.swsm.proxynet.*"})
public class ProxynetClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxynetClientApplication.class, args);
    }
    
}
