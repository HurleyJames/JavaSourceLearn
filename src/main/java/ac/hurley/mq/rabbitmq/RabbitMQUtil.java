package ac.hurley.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 有关RabbitMQ的工具类
 */
public class RabbitMQUtil {

    private static ConnectionFactory connectionFactory;

    // 静态代码块只需要执行一次
    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
    }

    // 定义连接对象的方法
    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 定义一个关闭通道和连接的方法
     * @param channel
     * @param connection
     */
    public static void close(Channel channel, Connection connection) {
        try {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
