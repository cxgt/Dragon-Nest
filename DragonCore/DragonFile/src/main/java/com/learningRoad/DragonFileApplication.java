package com.learningRoad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 文件服务
 * 
 * @author insocp
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DragonFileApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DragonFileApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Dragon 文件服务模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
