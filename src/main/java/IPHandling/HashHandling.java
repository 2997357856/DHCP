package IPHandling;

/**
 * @Author YZX
 * @Create 2023-03-30 19:35
 * @Java-version jdk1.8
 */
//用Hash思想处理数据
public class HashHandling {

    public static void main(String[] args) {
        HashHandling hhl = new HashHandling();
        System.out.println(hhl.getTableName("192.168.20.0"));
        System.out.println(hhl.getTableName("192.168.20.1"));
        System.out.println(hhl.getTableName("192.168.20.2"));
        System.out.println(hhl.getTableName("192.168.20.3"));
        System.out.println(hhl.getTableName("192.168.20.4"));
    }

    //用于插入ip：传入一个ip，返回数据库表的名称
    public String getTableName(String ip){
        ip = ip.replace(".","");
        //假设就创建三个数据库,分别为pools0,pools1,pools2
        //返回拼接的数据库id
        int tableId = Integer.parseInt(ip)%3;
        return "pools"+tableId;
    }

}
