package com.syf.test;

import com.syf.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestUnit {
    @Autowired
    RedisService redisService;

    @Test
    public void testRedis() {
//        String value = (String) redisService.get("18571731303");
//        System.out.println(value);
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        System.out.println(map.get("id"));
    }
}
