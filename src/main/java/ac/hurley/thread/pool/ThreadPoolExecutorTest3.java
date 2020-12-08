package ac.hurley.thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 创建一个定长线程池，支持定时及周期性任务执行。
 */
public class ThreadPoolExecutorTest3 {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": delay 1 seconds, and execute every 3 seconds");
            }
            // 延迟1秒后，每3秒执行一次
        }, 1, 3, TimeUnit.SECONDS);
    }
}
