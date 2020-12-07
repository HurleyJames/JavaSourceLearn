package ac.hurley.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 发送端
 */
public class Producer {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection = factory.newConnection();

        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        String message = getMessage(args);

        // 将消息标记为持久化的 PERSISTENT_TEXT_PLAIN
        channel.basicPublish("", TASK_QUEUE_NAME,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

//        channel.close();
//        connection.close();
        RabbitMQUtil.close(channel, connection);
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1) {
            return "Hello World";
        }
        return joinStrings(strings, " ");
    }

    /**
     * 将字符数组转化成一个字符串
     *
     * @param strings
     * @param delimiter
     * @return
     */
    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}


