package ac.hurley.db.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class Bloom {

    /**
     * 总个数
     */
    private static int total = 1000000;

    private static BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), total);

    public static void main(String[] args) {
        // 初始化1000000条数据到过滤器中
        for (int i = 0; i < total; i++) {
            filter.put(i);
        }
        int j = 5000;
        // 判断是否存在
        boolean flag = filter.mightContain(j);
        System.out.printf("%d 判断结果为：%b\n", j, flag);
        for (int i = 0; i < total; i++) {
            if (!filter.mightContain(i)) {
                System.out.println("有坏人逃脱");
            }
        }

        // 匹配不在过滤器中的10000个值做测试，看有多少个匹配了出来
        int count = 0;
        for (int i = total; i < total + 10000; i++) {
            if (filter.mightContain(i)) {
                count++;
            }
        }
        System.out.println("误伤的数量：" + count);
    }
}
