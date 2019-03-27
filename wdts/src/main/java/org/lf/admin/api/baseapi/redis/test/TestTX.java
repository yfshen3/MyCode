package org.lf.admin.api.baseapi.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestTX {
    public static void main(String[] args) {
        testMethod();
    }

    static boolean testMethod() {
        Jedis jedis = new Jedis("192.168.199.131", 6379);
        int balance;
        int amt = 10;

        jedis.watch("balance");
        balance = Integer.parseInt(jedis.get("balance"));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (balance < amt) {
            jedis.unwatch();
            System.out.println("modify");
            return false;
        } else {
            System.out.println("*********Transaction");
            Transaction tx = jedis.multi();
            tx.decrBy("balance", amt);
            tx.incrBy("debt", amt);
            tx.exec();
            System.out.println("balance:" + jedis.get("balance"));
            System.out.println("debt:" + jedis.get("debt"));
            return true;
        }
    }
}
