package ac.hurley.db.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RegularTest {

    private JedisPool jedisPool;

    public RegularTest() {
        this.jedisPool = new JedisPool();
    }

    public void execute(CallJedis caller) {
        // Java8的try-with-resource语法
        try (Jedis jedis = jedisPool.getResource()) {
            caller.call(jedis);
        }
    }

    public static void main(String[] args) {
        RegularTest regularTest = new RegularTest();
        regularTest.execute(jedis -> {
            // 执行Redis相关业务
            jedis.set("name", "Johnny");
            System.out.println(jedis.get("name"));
        });
    }
}

interface CallJedis {
    void call(Jedis jedis);
}