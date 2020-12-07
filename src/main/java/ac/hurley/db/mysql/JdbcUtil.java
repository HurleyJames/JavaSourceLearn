package ac.hurley.db.mysql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JdbcUtil {
    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    /**
     * 静态代码块
     */
    static {
        try {
            // 新建属性集对象
            Properties properties = new Properties();
            // 通过反射，新建字符输入流，读取db.properties文件
            InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
            // 将输入流中读取到的属性，加载到properties属性集对象中
            properties.load(is);
            // 根据键，获取properties中对应的值
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回数据库的连接
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            // 注册数据库的驱动
            Class.forName(driver);
            // 获取数据库的连接
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
