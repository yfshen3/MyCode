package org.lf.admin.api.baseapi.redis.test;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestRedis {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.199.131", 6379);

        /**
         * key
         */
        // 获取所有key
        Set<String> keys = jedis.keys("*");
        // 遍历输出所有key
        keys.forEach(key -> System.out.println(key));
        // 判断是否存在key
        System.out.println(jedis.exists("k2"));
        // 查看key的过期时间
        System.out.println(jedis.ttl("k2"));

        /**
         * string
         */
        // 追加
        jedis.append("k1", "myRedis");
        System.out.println(jedis.get("k1"));
        jedis.set("k1", "v1");
        jedis.mset("str1","v1","str2","v2");
        System.out.println(jedis.mget("str1", "str2"));

        /**
         * list
         */
        jedis.lpush("myList","v1","v2","v3","v4","v5");
        List<String> list = jedis.lrange("myList",0,-1);
        list.forEach(element -> System.out.println(element));

        /**
         * set
         */
        jedis.sadd("orders","jd001");
        jedis.sadd("orders","jd002");
        jedis.sadd("orders","jd003");
        Set<String> set1 = jedis.smembers("orders");
        set1.forEach(str -> System.out.println(str));
        jedis.srem("orders","jd002");
        System.out.println(jedis.smembers("orders").size());
        Set<String> set2 = jedis.smembers("orders");
        set2.forEach(str -> System.out.println(str));

        /**
         * hash
         */
        jedis.hset("hash1","userName","lisi");
        System.out.println(jedis.hget("hash1","userName"));

        Map<String,String> map = new HashMap<>(16);
        map.put("telephone","13811814763");
        map.put("address","wuhan");
        map.put("email","abc@163.com");
        jedis.hmset("hash2",map);
        List<String> result = jedis.hmget("hash2","telephone","email");
        result.forEach(element -> System.out.println(element));

        /**
         * zset
         */
        jedis.zadd("zset01",60d,"v1");
        jedis.zadd("zset01",70d,"v2");
        jedis.zadd("zset01",80d,"v3");
        jedis.zadd("zset01",90d,"v4");
        Set<String> s1 = jedis.zrange("zset01",0,-1);
        s1.forEach(s -> System.out.println(s));
    }
}