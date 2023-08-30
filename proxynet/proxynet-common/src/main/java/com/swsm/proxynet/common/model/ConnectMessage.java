package com.swsm.proxynet.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenxin
 * @date 2023/08/30 10:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectMessage {
    
    private String userId;
    private String targetIp;
    private Integer targetPort;
    
    
    
}
