package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yfshen
 */
public class ConditionDemo implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "waiting");
            condition.await();
            System.out.println(Thread.currentThread().getName() + "Thread is going on");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo demo = new ConditionDemo();
        Thread t = new Thread(demo);
        t.start();
        Thread.sleep(2000);
        // 通知线程t继续执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName());
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}
