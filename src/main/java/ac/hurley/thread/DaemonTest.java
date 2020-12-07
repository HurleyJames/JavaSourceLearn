package ac.hurley.thread;

import ac.hurley.util.SleepUtil;

/**
 * 如果一个Java虚拟机中存在Deamon线程时，Java虚拟机将会退出
 */
public class DaemonTest {

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        // 通过Thread.setDaemon(true)将线程设置为Daemon线程
        thread.setDaemon(true);
        // 需要在启动线程之前设置，而不能在启动线程之后设置
        thread.start();
    }

    static class DaemonRunner implements Runnable {

        @Override
        public void run() {
            try {
                SleepUtil.second(10);
            } finally {
                System.out.println("DaemonThread finally run");
            }
        }
    }
}
