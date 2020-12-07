package ac.hurley.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CASTest {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private int i = 0;

    public static void main(String[] args) {
        final CASTest cas = new CASTest();
        List<Thread> threads = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        cas.count();
                        cas.safeCount();
                    }
                }
            });
            threads.add(t);
        }
        for (Thread t : threads) {
            t.start();
        }

        // 等待所有线程执行完成
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(cas.i);
        System.out.println(cas.atomicInteger.get());
        // 花销的时间
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 使用CAS实现线程安全计数器
     */
    private void safeCount() {
        for (; ; ) {
            int i = atomicInteger.get();
            boolean success = atomicInteger.compareAndSet(i, ++i);
            if (success) {
                break;
            }
        }
    }

    /**
     * 非线程安全计数器
     */
    private void count() {
        i++;
    }
}
