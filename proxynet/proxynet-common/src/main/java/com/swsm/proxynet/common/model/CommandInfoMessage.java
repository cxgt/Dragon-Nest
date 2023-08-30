package com.swsm.proxynet.common.model;

import io.netty.channel.ChannelId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenxin
 * @date 2023/08/30 10:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandInfoMessage {
    
    private ChannelId userChannelId;
    private String targetIp;
    private Integer targetPort;
    private byte[] info;
    
    
}
