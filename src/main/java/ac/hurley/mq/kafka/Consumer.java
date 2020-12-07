package ac.hurley.mq.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

/**
 * 消费者
 */
public class Consumer {

    public static void main(String[] args) {
        Properties p = new Properties();
        p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.23.76:9092");
        p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // GROUP_ID_CONFIG表示消费者的分组，kafka是根据分组名称判断是否是同一组消费者
        p.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        // 初始化消费者KafkaConsumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(p);
        // 订阅消息，也可以订阅多个主题
        kafkaConsumer.subscribe(Collections.singletonList(Producer.topic));
        while (true) {
            // 循环拉取消息，poll传入的参数100是等待broker返回数据的时间。如果超过该时间无响应就不再等待
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            // 拉取消息后，循环进行处理
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("topic:%s, offset:%d, 消息:%s",
                        record.topic(), record.offset(), record.value()));
            }
        }
    }
}
