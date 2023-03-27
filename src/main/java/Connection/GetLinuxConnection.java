package Connection;

import ch.ethz.ssh2.Connection;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.IOException;

/**
 * @Author YZX
 * @Create 2023-03-24 15:51
 * @Java-version jdk1.8
 */
//用来连接到服务器
public class GetLinuxConnection {

    //连接测试
    public static void main(String[] args) {
        String ip = "192.168.31.104";
        int port = 22;
        String userName = "root";
        String password = "root";
        GetLinuxConnection GLC = new GetLinuxConnection();
        Session session = GLC.getJSchSession(ip,port,userName,password);
        GLC.closeJSchSession(session);
    }

    /**===================================第一种方法==================================================**/
    //ssh2创建连接
    public static Connection getConn(String ip , int port, String userName, String password){
        Connection conn = null;
        try {
            //创建连接
            conn = new Connection(ip,port);
            if (conn == null){
                throw new IOException("服务器连接失败");
            }
            //启用连接
            conn.connect();
            //用户认证
            boolean isAuthenticated = conn.authenticateWithPassword(userName, password);
            if (isAuthenticated == false){
                throw new IOException("用户认证失败");
            }
            System.out.println("服务器连接成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("服务器连接失败");
        }
        return conn;
    }

    //ssh2关闭连接
    public static void closeConn(Connection conn){
        if (conn != null) {
            try {
                conn.close();
                System.out.println("服务器连接关闭成功");
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("服务器连接关闭失败");
            }
        }
    }



    /**===================================第二种方法==================================================**/
    //jsch创建连接
    public Session getJSchSession(String ip , int port, String userName, String password){
        JSch jSch = new JSch();
        Session session = null;
        try {
            //创建连接
            session = jSch.getSession(userName,ip,port);
            session.setPassword(password);
            //是否使用密钥登录，一般默认为no
            session.setConfig("StrictHostKeyChecking", "no");
            //启用连接
            session.connect();
            System.out.println("==================服务器连接成功===================");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("==================服务器连接失败===================");
        }
        return session;
    }

    //jsch关闭连接
    public void closeJSchSession(Session session){
        if (session != null) {
            try {
                session.disconnect();
                System.out.println("================服务器连接关闭成功==================");
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("================服务器连接关闭失败==================");
            }
        }
    }

}
