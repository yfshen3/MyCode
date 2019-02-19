package com.syf.thread.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 要用到共同数据(包括同步锁)或共同算法的若干方法应该归在同一个身上，
 * 这种设计正好体现了高内聚和程序的健壮性
 * @author yfshen
 */
public class TraditionalCommunication {
    public static void main(String[] args) {
        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    business.sub(i);
                }
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            business.main(i);
        }
    }

}

class Business {
    public static final Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    boolean flag = true;

//    public synchronized void sub(int i) throws InterruptedException {
//        if (flag) {
//            wait();
//        }
//        for (int j = 1; j < 3; j++) {
//            System.out.println("sub:" + i + ";" + j);
//        }
//        flag = true;
//        notify();
//    }

    public void sub(int i) {
        try {
            lock.lock();
            /**
             * 用while比用if好。防止伪唤醒
             */
            while (flag) {
                condition.await();
            }
            for (int j = 1; j < 3; j++) {
                System.out.println("sub:" + i + ";" + j);
            }
            flag = true;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

//    public synchronized void main(int i) throws InterruptedException {
//        if (!flag) {
//            wait();
//        }
//        for (int j = 1; j < 6; j++) {
//            System.out.println("main:" + i + ";" + j);
//        }
//        flag = false;
//        notify();
//    }

    public void main(int i) {
        try {
            lock.lock();
            while (!flag) {
                condition.await();
            }
            for (int j = 1; j < 6; j++) {
                System.out.println("main:" + i + ";" + j);
            }
            flag = false;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}