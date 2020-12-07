package ac.hurley.designpattern.prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 原型模式就是从原型实例通过复制克隆出新的实例，而不是通过new一个类去实例化
 */
public class Client {

    /**
     * 痴汉模式实例化一个敌机的模型
     */
    private static EnemyPlane protoType = new EnemyPlane(200);

    /**
     * 简单地调用getInstance(int x)并声明x的坐标位置
     * 可以保证是在敌机出现的时候再去克隆，而不是一开局就全部克隆出来
     * 实时性地节省了内存空间，又保证了敌机实例化的速度
     *
     * @param x
     * @return
     * @throws CloneNotSupportedException
     */
    public static EnemyPlane getInstance(int x) throws CloneNotSupportedException {
        // 复制原型机
        EnemyPlane clone = protoType.clone();
        clone.setX(x);
        return clone;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        List<EnemyPlane> enemyPlaneList = new ArrayList<>();
//
//        // 如果我们循环的new新的敌机实例，CPU和内存被极大地浪费了
//        for (int i = 0; i < 50; i++) {
//            // 此处随机产生敌机的位置
//            EnemyPlane ep = new EnemyPlane(new Random().nextInt(200));
//            enemyPlaneList.add(ep);
//        }

        for (int i = 0; i < 50; i++) {
            // 可以保证是在敌机出现的时候再去克隆，而不是一开局就全部克隆出来
            EnemyPlane ep = getInstance(new Random().nextInt(200));
            enemyPlaneList.add(ep);
        }
    }
}
