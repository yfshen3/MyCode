package concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可中断
 * @author yfshen
 */
public class ReentrantLockInt implements Runnable{
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public ReentrantLockInt(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                lock1.lockInterruptibly();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId() + "线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockInt ri1 = new ReentrantLockInt(1);
        ReentrantLockInt ri2 = new ReentrantLockInt(2);
        Thread t1 = new Thread(ri1);
        Thread t2 = new Thread(ri2);
        t1.start();
        t2.start();
        Thread.sleep(1000);
        DeadLockCheck.check();
    }
}
