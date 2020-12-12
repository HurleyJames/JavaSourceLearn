package ac.hurley.db.redis;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache1<K, V> extends LinkedHashMap<K, V> {

    /**
     * 最大缓存值
     */
    private final int CACHE_SIZE;

    public LruCache1(int size) {
        // 第二个参数时负载因子
        // 传入true，存放元素的顺序按照最近访问的顺序存放，符合LRU
        // 传入false，存放元素的顺序按照元素添加的顺序存放
        super(size, 0.75f, true);
        CACHE_SIZE = size;
    }

    /**
     * 淘汰元素的方法
     *
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 淘汰条件就是存放元素的个数大于指定的最大元素个数进行淘汰
        return size() > CACHE_SIZE;
    }
}
