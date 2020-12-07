package ac.hurley.db.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

public class SimpleTest {

    public static void main(String[] args) {
        // 创建一个连接池
        JedisPool jedisPool = new JedisPool();
        // 从连接池中获取Jedis
        try (Jedis jedis = jedisPool.getResource()) {
            // 此处生成一个随机字符串并存入Redis
            jedis.set("token", UUID.randomUUID().toString());
            // 从Redis中获取key为token的字符串
            String token = jedis.get("token");
            System.out.println("token = " + token);
            // 测试连接
            String ping = jedis.ping();
            // 如果成功连接上了Redis服务，就输出ping
            System.out.println(ping);
            // 关闭连接
            jedis.close();
        }

    }
}
