package ac.hurley.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class MultiThreadTest {
    public static void main(String[] args) {
        // 获取Java线程管理的MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的monitor和synchronizer信息，仅仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        // 遍历线程信息，并打印
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
        }

        // 输出内容为（可能不同）：
        // [1] main                     main线程，用户程序的入口
        // [2] Reference Handler        清除Reference的线程
        // [3] Finalizer                调用对象finalize方法的线程
        // [4] Signal Dispatcher        分发处理发送给JVM方法的线程
        // [10] Common-Cleaner
        // [11] Monitor Ctrl-Break

        // 说明一个Java程序的运行除了main()方法之外，还有其他多个线程同时运行
    }
}
