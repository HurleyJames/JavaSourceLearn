package ac.hurley.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    /**
     * 队列名称
     */
    private final static String QUEUE_NAME = "helloMQ";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接，连接到消息队列
        ConnectionFactory factory = new ConnectionFactory();
        // 设置消息队列所在主机ip或者主机名
        factory.setHost("localhost");
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建一个频道
        Channel channel = connection.createChannel();
        // 指定一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 要发送的消息
        String message = "hello world";
        // 往队列中发送一条消息（信息的内容是字节数组，所以可以传递任何数据）
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}
