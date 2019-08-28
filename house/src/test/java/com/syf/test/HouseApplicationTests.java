package com.syf.test;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HouseApplicationTests {

    @Autowired
    private HttpClient httpClient;

    @Test
    public void testHttpClient() throws IOException {
        String str = EntityUtils.toString(httpClient.execute(new HttpGet("http://www.baidu.com")).getEntity());
        System.out.println(str);
    }
}
