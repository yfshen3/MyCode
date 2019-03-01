package test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AirThreadDemo2 {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new SynchronousQueue<>();
        Lock lock = new ReentrantLock();
//        Semaphore semaphore = new Semaphore(1);
        System.out.println(System.currentTimeMillis()/1000);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                lock.lock();
                try {
//                    semaphore.acquire();
                    String input = queue.take();
                    String output = doSome(input);
                    System.out.println(Thread.currentThread().getName() + ":" + output);
//                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            String input = i + "";
            try {
                queue.put(input);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static String doSome(String input) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String output = input + ":" + (System.currentTimeMillis()/1000);
        return output;
    }
}
