package ac.hurley.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LinkDatabaseInsert {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 注册数据库的驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 获取数据库连接（里面的内容，依次是"jdbc:mysql://主机名:端口号/数据库名", "用户名", "登录密码"）
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql",
                "root", "123456");
        String sql = "insert into stu(id, name, age) values(?,?,?)";
        // 获取预处理对象，并依次给参数赋值
        PreparedStatement statement = connection.prepareCall(sql);
        statement.setInt(1, 12);
        statement.setString(2, "小明");
        statement.setInt(3, 16);
        int i = statement.executeUpdate();
        System.out.println(i);
        // 关闭jdbc连接
        statement.close();
        connection.close();
    }
}
