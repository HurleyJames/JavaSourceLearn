package ac.hurley.thread.concurrent;

public class JoinCountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        Thread parser1 = new Thread(new Runnable() {
            @Override
            public void run() {
            }
        });

        Thread parser2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser2 finish");
            }
        });

        parser1.start();
        parser2.start();
        // join线程终止后，this.notifyAll()方法会被调用
        parser1.join();
        parser2.join();
        System.out.println("all parser finish");
    }
}

