package com.syf.thread.thread.multithreadsharedata;

/**
 * 如果每个线程执行的代码相同，可以使用同一个Runnable对象，
 * 这个Runnable对象中有那个共享数据，例如买票可以这么做。
 * @author yfshen
 */
public class MultiThreadShareData1 {
    public static void main(String[] args) {
        ShareData1 data1 = new ShareData1();
        new Thread(data1).start();
        new Thread(data1).start();
        new Thread(data1).start();
    }
}

class ShareData1 implements Runnable {
    private int count = 100;

    @Override
    public void run() {
        while (count > 0) {
            System.out.println(Thread.currentThread().getName() + ":" + count--);
        }
    }
}
