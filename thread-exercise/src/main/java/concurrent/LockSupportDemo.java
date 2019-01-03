package concurrent;

/**
 * 总结一下，LockSupport比Object的wait/notify有两大优势：
 *
 * ①LockSupport不需要在同步代码块里 。所以线程间也不需要维护一个共享的同步对象了，实现了线程间的解耦。
 *
 * ②unpark函数可以先于park调用，所以不需要担心线程间的执行的先后顺序。
 * @author yfshen
 */
public class LockSupportDemo {
    /**
     * 多次调用unpark方法和调用一次unpark方法效果一样，因为都是直接将_counter赋值为1，而不是加1。
     * 简单说就是：线程A连续调用两次LockSupport.unpark(B)方法唤醒线程B，然后线程B调用两次LockSupport.park()方法，
     * 线程B依旧会被阻塞。因为两次unpark调用效果跟一次调用一样，只能让线程B的第一次调用park方法不被阻塞，
     * 第二次调用依旧会阻塞。
     */
}
