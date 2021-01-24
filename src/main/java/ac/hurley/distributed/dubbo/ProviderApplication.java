package ac.hurley.distributed.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;

/**
 * 服务提供者，在服务提供者中，需要对外暴露API，从而Consumer可以调用到服务
 */
public class ProviderApplication {
    public static void main(String[] args) throws IOException {
        // 服务配置
        ServiceConfig<HelloService> service = new ServiceConfig<HelloService>();
        // 设置服务名
        service.setApplication(new ApplicationConfig("dubbo-provider"));
        // 设置注册中心
        service.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        // 设置服务对应接口
        service.setInterface(HelloService.class);
        // 设置服务对应实现
        service.setRef(new HelloServiceImpl());
        // 暴露服务
        service.export();
        System.out.println("first-dubbo-provider is running.");
        System.in.read();
    }
}
