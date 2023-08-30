package com.example.dataencryptionanddecryption.Util;

import cn.hutool.crypto.SmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author chenxin
 * @date 2023/08/24 15:17
 */
@Slf4j
@Component
public class Sm3Util {

    private static Integer httpCheckSignTimeOut = 1;//请求签名有效时间 默认1分钟

    /**
     * 获取实体类拼成的加密字段
     *
     * @param checkSign  前端传入的签名（从请求报文头获取）
     * @param signModel  查询的DTO模型类
     * @param privateKey 签名加密私钥
     * @param timestamp  时间戳（从请求报文头获取）
     * @return 比对结果
     */
    public boolean checkSign(String checkSign, Object signModel, String privateKey, Long timestamp) throws Exception {
        Long thisTime = System.currentTimeMillis() - timestamp;
        Integer checkSignTimeOut = httpCheckSignTimeOut;
        if (!(Objects.isNull(checkSignTimeOut) || checkSignTimeOut.intValue() == 0)) {
            //时间为0或者未配置签名超时时间，默认不验证时间戳
            if (thisTime >= 60 * 1000 * checkSignTimeOut || thisTime <= 0) {
                //checkSignTimeOut分钟内的时间戳才处理
                log.error("时间戳异常,非" + checkSignTimeOut + "分钟内请求,当前时间戳:" + System.currentTimeMillis());
                return false;
            }
        }
        String signValue = getSignValue(signModel) + "&timestamp=" + timestamp + "&privateKey=" + privateKey;
        String sign = getSign(signValue);
        log.info("【本地加密后 sm3 签名】" + sign);//生产上建议注释此行,防止泄露
        return sign.toUpperCase().equals(checkSign.toUpperCase()) ? true : false;
    }

    /**
     * 加密签名
     *
     * @param signValue 待加密签名字符串
     * @return 加密后签名字符串
     */
    public String getSign(String signValue) {
        return SmUtil.sm3(signValue);
    }

    /**
     * 获取实体类拼成的加密字段
     *
     * @param classA 传入参数实体类
     * @return 待加密字符串
     */
    public String getSignValue(Object classA) {
        Field[] fs = classA.getClass().getDeclaredFields();//获取所有属性
        String[][] temp = new String[fs.length][2]; //用二维数组保存  参数名和参数值
        for (int i = 0; i < fs.length; i++) {
            fs[i].setAccessible(true);
            temp[i][0] = fs[i].getName().toLowerCase(); //获取属性名
            try {
                temp[i][1] = String.valueOf(fs[i].get(classA));//把属性值放进数组
            } catch (Exception e) {
                log.error("【签名字段：" + fs[i].getName() + "添加失败】");
            }
        }
        temp = doChooseSort(temp); //对参数实体类按照字母顺序排续
        String result = "";
        for (int i = 0; i < temp.length; i++) {//按照签名规则生成待加密字符串
            result = result + temp[i][0] + "=" + temp[i][1] + "&";
        }
        result = result.substring(0, result.length() - 1);//消除掉最后的“&”
        log.info("【签名信息】{}", result);
        return result;
    }

    /**
     * 对二维数组里面的数据进行选择排序，按字段名按abcd顺序排列
     *
     * @param data 未按照字母顺序排序的二维数组
     * @return
     */
    private String[][] doChooseSort(String[][] data) {//排序方式为选择排序
        String[][] temp = new String[data.length][2];
        temp = data;
        int n = temp.length;
        for (int i = 0; i < n - 1; i++) {
            int k = i;// 初始化最小值的小标
            for (int j = i + 1; j < n; j++) {
                if (temp[k][0].compareTo(temp[j][0]) > 0) {    //下标k字段名大于当前字段名
                    k = j;// 修改最大值的小标
                }
            }
            // 将最小值放到排序序列末尾
            if (k > i) {  //用相加相减法交换data[i] 和 data[k]
                String tempValue;
                tempValue = temp[k][0];
                temp[k][0] = temp[i][0];
                temp[i][0] = tempValue;
                tempValue = temp[k][1];
                temp[k][1] = temp[i][1];
                temp[i][1] = tempValue;
            }
        }
        return temp;
    }

}
