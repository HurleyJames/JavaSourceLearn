package ac.hurley.util;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否为空的最优化代码
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否非空
     *
     * @param str
     * @return
     */
    public static boolean isNotNull(String str) {
        return !isNull(str);
    }
}

