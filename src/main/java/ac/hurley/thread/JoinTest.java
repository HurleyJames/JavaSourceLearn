package ac.hurley.thread;

import java.util.concurrent.TimeUnit;

/**
 * 每个线程终止的前提是前驱线程的终止，每个线程等待前驱线程终止后，才从join()方法返回
 */
public class JoinTest {

    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            // 每个线程都拥有一个线程的引用，需要等待前一个线程终止，才能从等待中返回
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }

    static class Domino implements Runnable {

        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                // 如果一个线程A执行了thread.join()语句，指的是
                // 当前线程A等待thread线程终止之后才能从thread.join()返回
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }
}
