package org.lf.admin.api.baseapi.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    JedisPool jedisPool;

    @Test
    // 添加和获取
    public void fun(){
        Jedis jedis = jedisPool.getResource();
        jedis.set("num", "1");
        System.out.println(jedis.get("num"));
        jedis.close();
    }

    @Test
    // 删除值
    public void fun1(){
        Jedis jedis = jedisPool.getResource();
        jedis.del("num");
        System.out.println(jedis.get("num"));
        jedis.close();
    }

    @Test
    //自减和自减
    public void fun2(){
        Jedis jedis = jedisPool.getResource();
        jedis.set("num","1");
        System.out.println(jedis.get("num"));
        jedis.decr("num");
        System.out.println(jedis.get("num"));
        jedis.incr("num");
        jedis.incr("num");
        System.out.println(jedis.get("num"));
    }
    @Test
    //加上/减去 一个数
    //incrBy 返回的是修改之后的值如果原值是字符串不是数字，则会抛出异常
    public void fun3(){
        Jedis jedis = jedisPool.getResource();
        Long num = jedis.incrBy("num", 3);
        System.out.println(num);
        jedis.decrBy("num",10);
        System.out.println(jedis.get("num"));
    }
    @Test
    //字符串拼接
    public void fun4(){
        Jedis jedis = jedisPool.getResource();
        jedis.set("name","caopengfei");
        Long len = jedis.append("name", "123");
        System.out.println(len);
        System.out.println(jedis.get("name"));
    }



    // ---------------hash---------------------
    //    hash 操作的是map对象
//    适合存储键值对象的信息
    @Test
    //存值 参数第一个变量的名称， map键名(key)， map键值(value)
//    调用hset
    public void hashfun() {
        Jedis jedis = jedisPool.getResource();
        Long num = jedis.hset("hash1", "username", "caopengfei");
        System.out.println(num);
        String hget = jedis.hget("hash1", "username");
        System.out.println(hget);
    }

    @Test
    //也可以存多个key
//    调用hmset
    public void hashfun1() {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", "caopengfei");
        map.put("age", "25");
        map.put("sex", "男");
        String res = jedis.hmset("hash2", map);
        System.out.println(res);//ok
    }

    @Test
    //获取hash中所有的值
    public void hashfun2() {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> map2 = new HashMap<String, String>();
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
    }

    @Test
//    删除hash中的键 可以删除一个也可以删除多个，返回的是删除的个数
    public void hashfun3() {
        Jedis jedis = jedisPool.getResource();
        Long num = jedis.hdel("hash2", "username", "age");
        System.out.println(num);
        Map<String, String> map2 = new HashMap<String, String>();
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
    }

    @Test
    //增加hash中的键值对
    public void hashfun4() {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> map2 = new HashMap<String, String>();
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
        jedis.hincrBy("hash2", "age", 10);
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
    }

    @Test
    //判断hash是否存在某个值
    public void hashfun5() {
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.hexists("hash2", "username"));
        System.out.println(jedis.hexists("hash2", "age"));
    }

    @Test
    //获取hash中键值对的个数
    public void hashfun6() {
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.hlen("hash2"));
    }

    //    获取一个hash中所有的key值
    @Test
    public void hashfun7() {
        Jedis jedis = jedisPool.getResource();
        Set<String> hash2 = jedis.hkeys("hash2");
        System.out.println(hash2);
    }

    //    获取所有的value值
    @Test
    public void hashfun8() {
        Jedis jedis = jedisPool.getResource();
        List<String> hash2 = jedis.hvals("hash2");
        System.out.println(hash2);
    }
}

