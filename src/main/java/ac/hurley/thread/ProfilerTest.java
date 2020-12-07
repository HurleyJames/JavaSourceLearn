package ac.hurley.thread;

import java.util.concurrent.TimeUnit;

/**
 * 通过set(T)方法设置一个值，在**当前线程**下再通过get()方法获取到原先设置的值
 */
public class ProfilerTest {

    public static void main(String[] args) throws Exception {
        ProfilerTest.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + ProfilerTest.end() + " mills");
    }

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }
}
