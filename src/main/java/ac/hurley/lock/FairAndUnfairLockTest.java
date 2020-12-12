package ac.hurley.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairAndUnfairLockTest {

    /**
     * 创建公平锁
     */
    private static Lock fairLock = new ReentrantLockDemo(true);

    /***
     * 创建非公平锁
     */
    private static Lock unfairLock = new ReentrantLockDemo(false);

    public static void main(String[] args) {
        FairAndUnfairLockTest fairAndUnfairLockTest = new FairAndUnfairLockTest();
        for (int i = 0; i < 5; i++) {
            // 公平锁测试
            fairAndUnfairLockTest.lockFairTask();
            // 非公平锁测试
            fairAndUnfairLockTest.lockUnfairTask();
        }
    }

    /**
     * 公平锁的线程
     */
    public void lockFairTask() {
        TaskThread taskThread = new TaskThread(fairLock, "FAIR");
        taskThread.start();
    }

    /**
     * 非公平锁的线程
     */
    public void lockUnfairTask() {
        TaskThread taskThread = new TaskThread(unfairLock, "UNFAIR");
        taskThread.start();
    }


    /**
     * 线程启动后打印线程信息
     */
    private static class TaskThread extends Thread {
        private Lock lock;
        private String type;

        public TaskThread(Lock lock, String type) {
            this.lock = lock;
            this.type = type;
        }

        @Override
        public void run() {
            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
                    System.out.println(type + " lock by [" + getId() + "], waiting by " +
                            ((ReentrantLockDemo) lock).getQueueThreads());
                } finally {
                    lock.unlock();
                }
            }
        }

        /**
         * 重写toString方法，使得线程打印出线程id来标识线程
         * @return
         */
        @Override
        public String toString() {
            return getId() + "";
        }
    }

    private static class ReentrantLockDemo extends ReentrantLock {
        public ReentrantLockDemo(boolean isFair) {
            super(isFair);
        }

        /**
         * 获取正在等待获取锁的线程列表
         *
         * @return
         */
        public Collection<Thread> getQueueThreads() {
            List<Thread> threadList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(threadList);
            return threadList;
        }


    }
}
