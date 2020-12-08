package ac.hurley.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理的需要，可灵活回收空闲线程，若无可回收，则新建线程
 */
public class ThreadPoolExecutorTest1 {
    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ": " + index);
                }
            });
        }
    }
}
