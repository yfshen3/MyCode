package com.syf.thread.thread.multithreadsharedata;

/**
 * 如果每个线程执行的代码不同，这时需要用不同的Runnable对象，
 * 有两种方式：
 * 第一种：将共享数据封装在另外一个对象中，然后这个对象逐一传
 * 递给各个Runnable对象。每个线程对共享数据的操作方法也分配
 * 到那份对于对象身上去完成，这样容易实现这对该数据进行的各个
 * 操作的互斥和通信
 * 第二种：将这些Runnable对象作为某一个类中的内部类，共享数据作为
 * 这个外部类中的成员变量，每个线程对共享数据的操作方法也分配给外部
 * 类，以便实现对共享数据进行的各个操作的互斥和通信，作为内部类的各
 * 个Runnable对象调用外部类的这些方法
 * @author yfshen
 *
 * 注释表示第一种，实际的是采用第二种
 */
public class MultiThreadShareData2 {
    private static ShareData2 data2 = new ShareData2();
    public static void main(String[] args) {
//        new Thread(() -> {
//            while (true) {
//                data2.inc();
//            }
//        }).start();
        new Thread(new IncRunnable(data2)).start();

//        new Thread(() -> {
//            while (true) {
//                data2.dec();
//            }
//        }).start();
        new Thread(new DecRunnable(data2)).start();
    }
}

class IncRunnable implements Runnable {
    private ShareData2 data2;
    public IncRunnable(ShareData2 data2) {
        this.data2 = data2;
    }

    @Override
    public void run() {
        while(true) {
            data2.inc();
        }
    }
}

class DecRunnable implements Runnable {
    private ShareData2 data2;
    public DecRunnable(ShareData2 data2) {
        this.data2 = data2;
    }

    @Override
    public void run() {
        while(true) {
            data2.dec();
        }
    }
}

class ShareData2 {
    public int count = 10;

    public synchronized void inc() {
        count ++;
        System.out.println("增加1：" + count);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void dec() {
        if (count > 0) {
            count --;
            System.out.println("减少1：" + count);
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
