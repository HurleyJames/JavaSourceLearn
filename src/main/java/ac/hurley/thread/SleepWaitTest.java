package ac.hurley.thread;

public class SleepWaitTest {

    public static void main(String[] args) {
        new Thread(new Thread1()).start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Thread2()).start();
    }

    private static class Thread1 implements Runnable {

        @Override
        public void run() {
            synchronized (SleepWaitTest.class) {
                System.out.println("thread1...");
                System.out.println("thread1 is waiting");

                try {
                    // 使用wait方式
                    SleepWaitTest.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("thread1 is going on...");
                System.out.println("thread1 is being over!");
            }
        }
    }

    private static class Thread2 implements Runnable {

        @Override
        public void run() {
            synchronized (SleepWaitTest.class) {
                System.out.println("thread2...");
                System.out.println("thread2 notify other thread can release wait status...");
                // 如果注释了下面这句，wait就不会释放锁，所以Thread1的wait后面的语句就不会执行
//                SleepWaitTest.class.notify()/notifyAll();
                System.out.println("thread2 is sleeping ten millisecond...");
            }

            try {
                // 使用sleep方式，检验是否释放锁，即后面的步骤是否会执行
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("thread2 is going on...");
            System.out.println("thread2 is being over!");
        }
    }
}
