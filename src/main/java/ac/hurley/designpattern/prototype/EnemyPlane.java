package ac.hurley.designpattern.prototype;

/**
 * 敌机类
 */
public class EnemyPlane implements Cloneable {
    /**
     * 敌机的横坐标
     */
    private int x;
    /**
     * 敌机的纵坐标
     */
    private int y = 0;

    public EnemyPlane(int x) {
        // 只接收横坐标的原因是，敌机一开始从顶部出来的时候纵坐标必然为0
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * 允许让克隆后的实例重新修改x横坐标
     * 保证了克隆飞机的个性化，即出现的位置是不同的
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * 飞行函数
     * 每次调用一次，敌机的纵坐标就+1
     */
    public void fly() {
        y++;
    }

    /**
     * 重写克隆的方法
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public EnemyPlane clone() throws CloneNotSupportedException {
        return (EnemyPlane) super.clone();
    }
}
