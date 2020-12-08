package ac.hurley.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newSingleThreadExecutor创建一个单线程化的线程池，它只会使用唯一的工作线程来执行任务，保证所有任务按先进先出顺序执行
 */
public class ThreadPoolExecutorTest4 {
    public static void main(String[] args) {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 结果依次输出，相当于顺序执行各个任务
                        System.out.println(Thread.currentThread().getName() + ": " + index);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
