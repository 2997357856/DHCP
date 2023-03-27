package pojo;

/**
 * @Author YZX
 * @Create 2023-03-25 15:24
 * @Java-version jdk1.8
 */
//ip实例
public class Ip {
    int id;
    String ip;

    @Override
    public String toString() {
        return "Ip{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                '}';
    }

    public Ip(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Ip() {
    }

    public Ip(int id, String ip) {
        this.id = id;
        this.ip = ip;
    }
}
