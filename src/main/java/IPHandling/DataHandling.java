package IPHandling;

import Connection.GetLinuxConnection;
import ExecCommands.ExecLinuxCommands;
import com.jcraft.jsch.Session;
import pojo.Ip;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-03-25 10:58
 * @Java-version jdk1.8
 */
//用来处理数据
public class DataHandling {

    //测试查询ip数据
    public static void main(String[] args) {
        String ip = "192.168.31.104";
        int port = 22;
        String userName = "root";
        String password = "root";
        List<String> cmds = new ArrayList<>();
        cmds.add("lxc list | cut -d \"|\" -f 4 |awk '/\\./'");
        GetLinuxConnection GLC = new GetLinuxConnection();
        Session session = GLC.getJSchSession(ip,port,userName,password);
        ExecLinuxCommands ELC = new ExecLinuxCommands();
        List<String> result = ELC.getCmdResult(session, cmds);
        DataHandling DHL = new DataHandling();
        List<Ip> processResult = DHL.process(result);
        processResult.forEach(x-> System.out.println(x));
        //关闭连接
        GLC.closeJSchSession(session);
    }

    //获取的值ip是一整个大的字符串，格式化为单条字符串，且筛选出自定义ip
    public List<Ip> process(List<String> sourceDataList){
        List<Ip> result = new ArrayList<>();
        String ips = sourceDataList.get(0);
        //利用空格进行拆分(因为不同的容器ip是四个空格分割，相同容器的ip是两个空格分割，所以我们拆出单独的ip)
        String[] splitIps = ips.split("\\s{2,4}");
        String line;
        for (int i = 0 ;i<splitIps.length;i++){
            // 此时获取的ip格式为: 192.168.31.25 (eth1)
            // 获取去除头尾空格和网卡接口，只要获取ip地址
            line = splitIps[i].trim().split(" ")[0];
            //自带ip一般为10.开头，我们只获取自定义网卡接口，即192.168开头的ip地址
            if (line.startsWith("192.168")){
                Ip ip = new Ip(line);
                result.add(ip);
            }
        }
        return result;
    }

}
