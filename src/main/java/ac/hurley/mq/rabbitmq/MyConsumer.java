package ac.hurley.mq.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 接收端
 */
public class MyConsumer {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();
        boolean isDurable = true;
        // 将所有的队列和消息设置成持久化
        channel.queueDeclare(TASK_QUEUE_NAME, isDurable, false, false, null);
        System.out.println(" [*] Waiting fo messages. To exit press CTRL + C");

        // 同一时刻，服务器只会发送一条信息给消费者，即只有在消费者空闲的时候才会发送下一条信息
        // 消费者在接收到队列里的消息但还没有返回确认结果之前，队列都不会将新的消息分发给消费者
        // 队列中没有被消费的信息并不会被删除，而是存在于队列之中
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {
//            @Override
            public void handleDelivery(String customTag,
                                       Envelope envelope,
                                       BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                } finally {
                    System.out.println(" [x] Done");
                    // 返回确认状态
                    // 在每次处理完成一个消息后，手动发送一次应答
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }

    /**
     * 在发送到队列的消息的末尾添加一定数量的点，每个点代表在工作线程中需要耗时1秒
     *
     * @param task
     */
    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
