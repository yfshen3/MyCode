package concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yfshen
 */
public class ReentrantLockDemo1 implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for(int j = 0; j < 1000000; j++) {
           lock.lock();
           lock.lock();
           try {
               i++;
           } finally {
               lock.unlock();
               lock.unlock();
           }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo1 rt = new ReentrantLockDemo1();
        Thread t1 = new Thread(rt);
        Thread t2 = new Thread(rt);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
