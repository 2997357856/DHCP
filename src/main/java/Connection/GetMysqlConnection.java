package Connection;

import java.sql.*;

/**
 * @Author YZX
 * @Create 2023-03-25 11:49
 * @Java-version jdk1.8
 */
//用来连接Linux中的Mysql数据库
public class GetMysqlConnection {

    public static void main(String[] args) throws SQLException {
        GetMysqlConnection connection = new GetMysqlConnection();
        connection.getConnection();
        connection.getConnection().close();
    }

    //连接数据库
    public Connection getConnection(){
        String url="jdbc:mysql://192.168.31.104:3306/DHCPPools?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
        String user="root";
        String password="root";
        Connection conn = null;
        try {
            //1. 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2. 获取连接
            conn = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


}
