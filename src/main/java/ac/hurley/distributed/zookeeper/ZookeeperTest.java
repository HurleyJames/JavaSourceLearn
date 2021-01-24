package ac.hurley.distributed.zookeeper;

import java.util.List;

public class ZookeeperTest {

    public static void main(String[] args) throws Exception {
        BaseZookeeper zookeeper = new BaseZookeeper();
        zookeeper.connectZookeeper("192.168.0.1:2181");
        List<String> children = zookeeper.getChildren("/");
        System.out.println(children);
    }
}
