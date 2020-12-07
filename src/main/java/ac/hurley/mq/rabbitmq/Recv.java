package ac.hurley.mq.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv {

    /**
     * 队列名称
     */
    private final static String QUEUE_NAME = "helloMQ";

    public static void main(String[] args) throws Exception {
        // 打开连接，创建频道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明队列，主要是为了防止消息接收者先运行此程序，队列还不存在时才创建队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL + C");

        // 创建消费者
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag,
//                                       Envelope envelope,
//                                       BasicProperties properties,
//                                       byte[] body) throws IOException {
//                String message = new String(body, "UTF-8");
//                System.out.println(" [x] Received '" + message + "'");
//            }
//        };

        DeliverCallback callback = (String consumerTag, Delivery delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {});
    }
}
