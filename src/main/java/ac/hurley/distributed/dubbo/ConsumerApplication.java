package ac.hurley.distributed.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

/**
 * Dubbo提供了注解、XML方式来暴露以及管理服务，所以不用每次调用需要编写获取实例的过程
 * 使用ZooKeeper作为注册中心
 */
public class ConsumerApplication {

    public static void main(String[] args) {
        // 声明引用配置
        ReferenceConfig<HelloService> reference = new ReferenceConfig<>();
        // 设置应用名字
        reference.setApplication(new ApplicationConfig("dubbo-consumer"));
        // 设置注册中心，从而可以监听服务
        reference.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        // 设置服务名，即服务提供者锁暴露的名字
        reference.setInterface(HelloService.class);
        // 获取一个代理实例
        HelloService service = reference.get();
        // 调用实例，执行方法
        String message = service.hello("dubbo I am hurley");
        System.out.println(message);
    }
}
