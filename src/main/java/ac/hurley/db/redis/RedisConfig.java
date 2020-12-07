//package ac.hurley.db.redis;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//@Configuration
//public class RedisConfig {
//
//    /**
//     * 服务器
//     */
//    @Value("${spring.redis.host}")
//    private String host;
//    /**
//     * 端口
//     */
//    @Value("${spring.redis.port}")
//    private int port;
//    /**
//     * 密码
//     */
//    @Value("${spring.redis.password}")
//    private String password;
//    /**
//     * 超时时间
//     */
//    @Value("${spring.redis.timeout}")
//    private String timeout;
//    /**
//     * 最大连接数
//     */
//    @Value("${spring.redis.jedis.pool.max-active}")
//    private int maxTotal;
//    /**
//     * 最大连接阻塞等待时间
//     */
//    @Value("${spring.redis.jedis.pool.max-wait}")
//    private String maxWaitMills;
//    /**
//     * 最大空闲连接
//     */
//    @Value("${spring.redis.jedis.pool.max-idle}")
//    private int maxIdle;
//    /**
//     * 最小空闲连接
//     */
//    @Value("${spring.redis.jedis.pool.min-idle}")
//    private int minIdle;
//
//    static {
//        // 读取配置文件
//        InputStream is = RedisConfig.class.getClassLoader().getResourceAsStream("jedis.properties");
//        // 创建Properties对象
//        Properties properties = new Properties();
//        // 关联文件
//        try {
//            properties.load(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Bean
//    public JedisPool redisPoolFactory() {
//        // 配置JedisPoolConfig
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        // 注意值的改变
//        jedisPoolConfig.setMaxWaitMillis(Long.parseLong(maxWaitMills.substring(0, maxWaitMills.length() - 2)));
//        // 注意属性名
//        jedisPoolConfig.setMaxTotal(maxTotal);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMinIdle(minIdle);
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host,
//                port, Integer.parseInt(timeout.substring(0, timeout.length() - 2)), password);
//        return jedisPool;
//    }
//}
