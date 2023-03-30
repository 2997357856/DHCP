package ExecCommands;

import Connection.GetMysqlConnection;
import IPHandling.HashHandling;
import pojo.Ip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-03-25 15:19
 * @Java-version jdk1.8
 */

//处理数据库语句
public class ExecMysqlCommands {

    //测试
    public static void main(String[] args) throws SQLException {
        ExecMysqlCommands EMC = new ExecMysqlCommands();
        HashHandling HHL = new HashHandling();
        Ip ip1 = new Ip("192.168.66.80");
        Ip ip2 = new Ip("192.168.66.81");
        Ip ip3 = new Ip("192.168.66.82");
        Ip ip4 = new Ip("192.168.66.83");
        Ip ip5 = new Ip("192.168.66.84");
        List<Ip> ipList0 = new ArrayList<>();
        ipList0.add(ip1);
        ipList0.add(ip2);
        ipList0.add(ip3);
        ipList0.add(ip4);
        ipList0.add(ip5);
        System.out.println("插入成功");
        EMC.insertIpList(ipList0,"pools0");
    }


    //获取数据库中存储的ip集合
    public List<Ip> getIpList(String tableName) throws SQLException {
        //获取数据库的连接对象
        GetMysqlConnection GMC = new GetMysqlConnection();
        Connection conn = GMC.getConnection();
        List<Ip> list = new ArrayList<>();
        //传递sql语句，创建时不需要传递sql语句，执行时传入，一般用来查询全部
        Statement stmt = conn.createStatement();
        //根据传入的数据表名称，获取结果集的遍历
        ResultSet rs = stmt.executeQuery("select id,ip from "+tableName);
        while (rs.next()){
            Ip ip = new Ip(rs.getInt("id"),rs.getString("ip"));
            list.add(ip);
        }
        //关闭连接
        rs.close();
        stmt.close();
        conn.close();
        return list;
    }

    //插入单个ip数据,返回值表示是否执行成功
    public void insertIp(Ip ip,String tableName) throws SQLException {
        //获取数据库的连接对象
        GetMysqlConnection GMC = new GetMysqlConnection();
        Connection conn = GMC.getConnection();
        String sql = "insert into "+tableName+" values(?,?)";
        //用来接受插入语句，创建时就需要传递sql语句，执行的时候不需要传递sql语句，一般用来插入
        PreparedStatement ps = conn.prepareStatement(sql);
        //id自增，随便插入即可
        ps.setInt(1,0);
        ps.setString(2,ip.getIp());
        ps.execute();
        //关闭连接
        ps.close();
        conn.close();
    }


    //插入多个ip数据
    public void insertIpList(List<Ip> ipList,String tableName) throws SQLException {
        //获取数据库的连接对象
        GetMysqlConnection GMC = new GetMysqlConnection();
        Connection conn = GMC.getConnection();
        String sql = "insert into "+tableName+" values(?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0 ;i<ipList.size();i++){
            //这里id自增，所以随便插入即可
            ps.setInt(1,0);
            ps.setString(2,ipList.get(i).getIp());
            //添加批处理命令
            ps.addBatch();
            //每两句插入一次
            if (i%2==0){
                ps.executeBatch();
            }
        }
        //防止没有执行完毕
        ps.executeBatch();
        //先清空批处理命令
        ps.clearBatch();
        //关闭连接
        ps.close();
        conn.close();
    }


}
