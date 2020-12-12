package ac.hurley.db.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LruCache2 {

    private DoubleList doubleList;
    private Map<Integer, Node<Integer, Integer>> map;
    private final int CACHE_SIZE;

    public static void main(String[] args) {
        LruCache2 lru = new LruCache2(2);
        lru.put(1, 3);
        lru.put(2, 4);
        lru.get(1);
        lru.put(3, 5);
        System.out.println(lru.keySet());
    }

    public LruCache2(int size) {
        CACHE_SIZE = size;
        doubleList = new DoubleList();
        map = new HashMap<>();
    }

    public void put(int key, int value) {
        Node<Integer, Integer> node = map.get(key);
        if (node == null) {
            // 添加数据，添加到队尾
            Node<Integer, Integer> node1 = new Node<>(key, value);
            map.put(key, node1);
            doubleList.addLast(node1);
        } else {
            // 先删除旧数据
            doubleList.removeNode(node);
            // 更新值
            node.v = value;
            doubleList.addLast(node);
        }
        // 判断存放元素大小是否超出阈值
        if (map.size() > CACHE_SIZE) {
            // 淘汰掉队首的数据，即最近最少未使用的数据
            Node head = doubleList.head.next;
            doubleList.removeNode(head);
            // 同时将map中对应的数据也清除掉
            map.remove(head.k);
        }
    }

    public int get(int key) {
        Node<Integer, Integer> node = map.get(key);
        if (node == null) {
            throw new RuntimeException("Key不存在");
        }
        // 使用过该数据，则需要将数据移动到队尾
        doubleList.removeNode(node);
        doubleList.addLast(node);
        return node.v;
    }

    public Set<Integer> keySet() {
        return map.keySet();
    }
}

class Node<K, V> {
    K k;
    V v;
    Node next;
    Node prev;

    public Node() {
        next = prev = null;
    }

    public Node(K k, V v) {
        this.k = k;
        this.v = v;
        next = prev = null;
    }
}

/**
 * 双向链表
 */
class DoubleList {
    Node head;
    Node tail;

    public DoubleList() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 将数据添加到队尾
     *
     * @param node
     */
    public void addLast(Node node) {
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node;
        tail.prev = node;
    }

    /**
     * 删除队列中的数据
     *
     * @param node
     */
    public void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

}