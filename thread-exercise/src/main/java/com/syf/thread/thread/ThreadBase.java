package com.syf.thread.thread;

/**
 * 多线程执行效率不一定比单线程高，
 * 比如拷贝文件，一下拷贝整个文件夹比进入文件夹同时拷贝多个文件效率高
 *
 * 多线程下载比较快的原因是抢了服务器的带宽，服务器资源是固定的
 */
public class ThreadBase {
    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable:" + Thread.currentThread().getName());
            }
        }){
            @Override
            public void run() {
                System.out.println("thread:" + Thread.currentThread().getName());
            }
        }.start();
    }
}
