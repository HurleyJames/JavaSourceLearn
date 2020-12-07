package ac.hurley.util;

import java.io.StringReader;

/**
 * 数据类型转换类
 */
public class CastUtil {

    /**
     * 转化为String类型，如果格式不对就返回""
     *
     * @param obj
     * @return
     */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /**
     * 转化为String类型
     * 将obj转化为String，如果obj为null就返回default
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转化为double类型，如果为null或者空字符串或者格式不对就返回0
     *
     * @param obj
     * @return
     */
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    /**
     * 转化为double类型，如果为null或者空字符串或者格式不对就返回defaultValue
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static double castDouble(Object obj, double defaultValue) {
        // 声明value，将defaultValue的值赋给value
        double value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotNull(strValue)) {
                try {
                    value = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转化为long型数据，如果obj为null或者空字符串或者格式不对就返回0
     *
     * @param obj
     * @return
     */
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    /**
     * 转化为long型数据，如果obj为null或者空字符串或者格式不对就返回defaultValue
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static long castLong(Object obj, long defaultValue) {
        long value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotNull(strValue)) {
                try {
                    value = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转化为int模型，如果不对就返回0
     *
     * @param obj
     * @return
     */
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    /**
     * 转化为int模型，如果不对就返回defaultValue
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static int castInt(Object obj, int defaultValue) {
        int value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotNull(strValue)) {
                try {
                    value = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转化为boolean类型，提供默认值false
     *
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    /**
     * 转化为boolean类型，提供默认值defaultValue
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean value = defaultValue;
        if (obj != null) {
            // 源码底层会把字符串和castString(obj)进行比较，所以无需判断字符串是否为空
            value = Boolean.parseBoolean(castString(obj));
        }
        return value;
    }
}
