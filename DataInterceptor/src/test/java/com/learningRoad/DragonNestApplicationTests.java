package com.learningRoad;

import com.alibaba.fastjson2.JSONObject;
import com.learningRoad.dao.InfoNbrUserMapper;
import com.learningRoad.entity.InfoNbrUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DragonNestApplicationTests {

    @Autowired(required = false)
    private InfoNbrUserMapper infoNbrUserMapper;
    @Test
    void contextLoads() {
        InfoNbrUser infoNbrUser = infoNbrUserMapper.queryInfoNbrUser("1231231");
        System.out.println("----------");
        System.out.println("---------"+ JSONObject.toJSONString(infoNbrUser));
    }

}
