package ac.hurley.thread;

/**
 * 下面的代码并发执行一定比串行执行快吗
 * <p>
 * 不一定
 * <p>
 * 因为线程有创建和上下文切换的开销
 */
public class ConcurrencyTest {
    private static final long count = 100001;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    /**
     * 并发执行
     *
     * @throws InterruptedException
     */
    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        // 下面这块区域变黄是因为线程资源必须由线程池提供，而不应该自行创建
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = 0;
                for (long i = 0; i < count; i++) {
                    a += 5;
                }
            }
        });

        thread.start();
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        thread.join();
        System.out.println("concurrency: " + time + "ms, b=" + b);
    }

    /**
     * 串行执行
     */
    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial: " + time + "ms, b=" + b);
    }
}
