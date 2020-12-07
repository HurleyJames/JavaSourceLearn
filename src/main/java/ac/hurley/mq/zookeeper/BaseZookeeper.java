package ac.hurley.mq.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BaseZookeeper implements Watcher {

    private ZooKeeper zooKeeper;

    /**
     * 超时时间
     */
    private static final int SECOND_TIME_OUT = 2000;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent watchedEvent) {
        // 如果状态是连接加载中
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("Watch received event");
            countDownLatch.countDown();
        }
    }

    /**
     * 连接到zookeeper
     *
     * @param host
     * @throws Exception
     */
    public void connectZookeeper(String host) throws Exception {
        Watcher watcher;
        zooKeeper = new ZooKeeper(host, SECOND_TIME_OUT, this);
        countDownLatch.await();
        System.out.println("zookeeper connection success");
    }

    /**
     * 创建节点
     *
     * @param path
     * @param data
     * @return
     * @throws Exception
     */
    public String createNode(String path, String data) throws Exception {
        return this.zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 获取路径下的所有子节点
     *
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(path, false);
        return children;
    }

    /**
     * 获取节点上面的数据
     *
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getData(String path) throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData(path, false, null);
        if (data == null) {
            return "";
        }
        return new String(data);
    }

    /**
     * 设置节点信息
     *
     * @param path
     * @param data
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat setData(String path, String data) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.setData(path, data.getBytes(), -1);
        return stat;
    }

    /**
     * 删除节点
     *
     * @param path
     * @throws InterruptedException
     * @throws KeeperException
     */
    public void deleteNode(String path) throws InterruptedException, KeeperException {
        zooKeeper.delete(path, -1);
    }

    /**
     * 获取创建的时间
     *
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getCreateTime(String path) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path, false);
        return String.valueOf(stat.getCtime());
    }

    /**
     * 获取某个路径下孩子的数量
     *
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public int getChildrenNum(String path) throws KeeperException, InterruptedException {
        int childNum = zooKeeper.getChildren(path, false).size();
        return childNum;
    }

    /**
     * 关闭连接
     *
     * @throws InterruptedException
     */
    public void closeConnection() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
        }
    }
}

