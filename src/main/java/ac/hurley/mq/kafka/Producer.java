package ac.hurley.mq.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;

/**
 * 生产者
 */
public class Producer {
    public static final String topic = "test";

    public static void main(String[] args) {
        Properties p = new Properties();
        // kafka如果是集群，多个地址就需要用多个逗号分隔开
        // put方法的第一个参数其实是字符串
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.23.76:9092,192.168.23.77:9092");
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 初始化KafkaProducer对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(p);

        try {
            while (true) {
                String msg = "Hello, " + new Random().nextInt(100);
                // 创建要发送的消息对象
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, msg);
                // 可以通过返回的Future来判断是否已经发送到了kafka
                kafkaProducer.send(record);
                System.out.println("消息发送成功：" + msg);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            kafkaProducer.close();
        }
    }
}
