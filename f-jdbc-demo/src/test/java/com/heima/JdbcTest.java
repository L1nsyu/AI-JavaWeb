package com.heima;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


import com.itheima.User; // 确保这个包路径与您的项目结构一致

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {
    /*
     * jdbc入门程序
     * */
    @Test
    public void testUpdate() throws Exception {
        //1.注册驱动
        //Class.forName() : 指定某一类加入到内存当中
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取数据库连接
        String url = "jdbc:mysql://localhost:3306/web01";
        String username = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, username, password);

        //3.获取SQL语句执行对象
        //创建一个用于向数据库发送和执行 SQL 语句的对象.
        Statement statement = connection.createStatement();

        //4.执行SQL
        int i = statement.executeUpdate("update user set age = 25 where id = 1");//DML
        System.out.println("SQl执行完毕影响的记录数为:" + i);

        //5.释放资源
        statement.close();
        connection.close();
    }


    @Test
    public void testSelect() {
        // 数据库连接信息
        String URL = "jdbc:mysql://localhost:3306/web01";
        String USER = "root";
        String PASSWORD = "123456";

        // 声明JDBC对象
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 1. 注册 JDBC 驱动 (对于现代驱动是可选的)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 打开链接
            System.out.println("Connecting to database...");
            //获取数据库连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // 3. 执行查询
            String sql = "SELECT id, username, password, name, age FROM user WHERE username = ? AND password = ?";

            stmt = conn.prepareStatement(sql);

            // 为参数占位符设置值
            stmt.setString(1, "daqiao");
            stmt.setString(2, "123456");

            rs = stmt.executeQuery();//DQL

            // 4. 处理结果集
            while (rs.next()) {
                // 为每一行记录创建一个新的User对象
                User user = new User();

                // 从结果集中获取数据并封装到User对象中
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));

                // 使用 Lombok 的 @Data 自动生成的 toString 方法打印User对象
                System.out.println(user);
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            System.err.println("数据库操作失败！");
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            System.err.println("驱动加载失败或发生其他异常！");
            e.printStackTrace();
        } finally {
            // 5. 关闭资源
            // finally 块用于关闭资源，以确保它们总是在完成后关闭
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}

