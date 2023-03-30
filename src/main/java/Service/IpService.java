package Service;

import Connection.GetLinuxConnection;
import ExecCommands.ExecLinuxCommands;
import ExecCommands.ExecMysqlCommands;
import IPHandling.DataHandling;
import com.jcraft.jsch.Session;
import pojo.Ip;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-03-25 16:15
 * @Java-version jdk1.8
 */
//ip的操作服务
public class IpService {


    String ip = "192.168.31.104";
    int port = 22;
    String userName = "root";
    String password = "root";
    //获取Linux连接
    GetLinuxConnection GLC = new GetLinuxConnection();
    //获取Linux的命令处理对象
    ExecLinuxCommands ELC = new ExecLinuxCommands();
    //获取IP地址处理对象
    DataHandling DHL = new DataHandling();
    //获取Mysql的命令处理对象
    ExecMysqlCommands EMC = new ExecMysqlCommands();


    public static void main(String[] args) throws SQLException {
        IpService is = new IpService();
        List<String> cmds = new ArrayList<>();
        cmds.add("lxc list | cut -d \"|\" -f 4 |awk '/\\./'");
        is.searchAndInsertIp(cmds);
    }


    public void searchAndInsertIp(List<String> commands) throws SQLException {
        //第一步：获取Linux连接对象
        Session session = GLC.getJSchSession(ip, port, userName, password);
        //第二步：传递命令查询ip
        List<String> cmdResult = ELC.getCmdResult(session, commands);
        //第三步：处理查询的ip
        List<Ip> ipList = DHL.process(cmdResult);
        //第四步：将处理的ip存入数据库中
        EMC.insertIpList(ipList,"pools0");
        System.out.println("插入ip地址成功,共插入"+ipList.size()+"条");
        ipList.forEach(x-> System.out.println(x));
        //第五步：查询是否插入成功
        //List<Ip> ipResult = EMC.getIpList();
        //System.out.println("当前数据库ip共"+ipResult.size()+"条,展示如下");
        //ipResult.forEach(x-> System.out.println(x));
        //关闭连接对象
        GLC.closeJSchSession(session);
    }
}
