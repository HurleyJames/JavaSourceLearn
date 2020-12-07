package ac.hurley.db.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HiveJDBC {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    private static String url = "jdbc:hive2://192.168.134.153:10000/mydb";

    private static String user = "root";

    private static String password = "root";

    private static Connection conn = null;

    private static Statement stmt = null;

    private static ResultSet rs = null;

    /**
     * 初始化连接
     *
     * @throws Exception
     */
    public void init() throws Exception {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, user, password);
        stmt = conn.createStatement();
    }

    /**
     * 创建数据库的语句
     *
     * @throws Exception
     */
    public void createDatabase() throws Exception {
        String sql = "create database hive_jdbc_test";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    /**
     * 删除数据库
     *
     * @throws Exception
     */
    public void dropDatabase() throws Exception {
        String sql = "drop database if exists hive_jdbc_test";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    /**
     * 显示数据库的内容
     *
     * @throws Exception
     */
    public void showDatabases() throws Exception {
        String sql = "show databases";
        System.out.println("Running: " + sql + "\n");
        // ResultSet
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    /**
     * 创建数据库表结构
     *
     * @throws Exception
     */
    public void createTable() throws Exception {
        String sql = "create table t2(id int, name String) row format " +
                "delimited fields terminated by ',';";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    /**
     * 加载数据
     *
     * @throws Exception
     */
    public void loadData() throws Exception {
        String filePath = "/usr/tmp/student";
        String sql = "load data local in path '" + filePath + "' overwrite into table t2";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    /**
     * select语句显示查询出的内容
     *
     * @throws Exception
     */
    public void selectData() throws Exception {
        String sql = "select * from t2";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        System.out.println("编号" + "\t" + "姓名");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
        }
    }

    /**
     * 删除表
     *
     * @param stmt
     * @throws Exception
     */
    public static void drop(Statement stmt) throws Exception {
        String dropSQL = "drop table t2";
        boolean bool = stmt.execute(dropSQL);
        System.out.println("删除表是否成功：" + bool);
    }

    /**
     * 销毁并且关闭连接
     *
     * @throws Exception
     */
    public void destory() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}


