package com.swsm.proxynet.common.model;

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
public class CommandMessage {
    private String type;
    private String message;
}
